package com.microservice.auth.service;

import com.microservice.auth.repository.UserRepository;
import com.microservice.constants.ExceptionCodeEnum;
import com.microservice.exceptionManagement.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = users.findByUsernameAndIsActiveTrue(username)
                .orElseThrow(() -> new BaseException(ExceptionCodeEnum.USER_NOT_FOUND));

        var roleAuthorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .collect(Collectors.toSet());

        var permAuthorities = user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(p -> new SimpleGrantedAuthority("SCOPE_" + p.getName()))
                .collect(Collectors.toSet());

        roleAuthorities.addAll(permAuthorities);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(roleAuthorities)
                .disabled(Boolean.FALSE.equals(user.getIsActive()))
                .accountLocked(false)
                .build();
    }

}
