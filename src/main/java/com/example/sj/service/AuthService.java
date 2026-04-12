package com.example.sj.service;

import com.example.sj.dto.LoginRequest;
import com.example.sj.dto.AuthResponse;
import com.example.sj.dto.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
}
