package com.microservice.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(".well-known")
@RequiredArgsConstructor
class JwksController {
    private final com.nimbusds.jose.jwk.RSAKey rsaKey;

    @GetMapping(value = "jwks.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> jwks() {
        return new com.nimbusds.jose.jwk.JWKSet(rsaKey.toPublicJWK()).toJSONObject();
    }
}
