package com.example.sj.service;

import com.example.sj.dto.AuthResponse;
import com.example.sj.dto.RegisterRequest;

public interface RegistrationService {
    /**
     * Register a new user with role-specific profile creation
     * 
     * ১. মেইন User Entity তৈরি ও সেভ করা
     * ২. রোল অনুযায়ী বাকি ডাটা সেভ করা (Seller/Customer)
     * 
     * @param registerRequest Registration request with user and role-specific data
     * @return AuthResponse with token and user info
     */
    AuthResponse register(RegisterRequest registerRequest);
}
