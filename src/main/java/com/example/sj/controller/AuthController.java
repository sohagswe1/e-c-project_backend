package com.example.sj.controller;

import com.example.sj.dto.LoginRequest;
import com.example.sj.dto.RegisterRequest;
import com.example.sj.dto.AuthResponse;
import com.example.sj.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.login(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            AuthResponse errorResponse = new AuthResponse();
            errorResponse.setMessage("Login failed: " + ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        try {
            AuthResponse response = authService.register(registerRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            AuthResponse errorResponse = new AuthResponse();
            errorResponse.setMessage("Registration failed: " + ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
