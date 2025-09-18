package com.microservice.auth.service;

import com.microservice.auth.dto.LoginRequest;
import com.microservice.auth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    @Value("${security.jwt.access-token-ttl-sec:3600}")
    private long accessTokenTtlSec;

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        Set<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith("ROLE_"))
                .map(a -> a.substring("ROLE_".length()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<String> permissions = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith("SCOPE_") || a.startsWith("PERM_"))
                .map(a -> a.replaceFirst("^(SCOPE_|PERM_)", ""))
                .collect(Collectors.toCollection(LinkedHashSet::new));


        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessTokenTtlSec);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(request.username())
                .issuedAt(now)
                .expiresAt(exp)
                .claim("ROLE", roles)
                .claim("PERMISSION", permissions)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(token, "Bearer", exp);
    }
}
