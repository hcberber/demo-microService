package com.microservice.auth.service;


import com.microservice.auth.dto.LoginRequest;
import com.microservice.auth.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
