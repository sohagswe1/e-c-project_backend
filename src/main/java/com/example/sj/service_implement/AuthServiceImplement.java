package com.example.sj.service_implement;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sj.dto.AuthResponse;
import com.example.sj.dto.LoginRequest;
import com.example.sj.dto.RegisterRequest;
import com.example.sj.entity.User;
import com.example.sj.repository.UserRepository;
import com.example.sj.security.JwtTokenProvider;
import com.example.sj.service.AuthService;
import com.example.sj.service.RegistrationService;

/**
 * Authentication Service - Handle login and registration
 * 
 * Delegates registration to RegistrationService for clean separation of concerns
 * 
 * @author Application Team
 * @version 2.0
 */
@Service
public class AuthServiceImplement implements AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private RegistrationService registrationService;
    
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            
            String token = jwtTokenProvider.generateToken(authentication);
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            return AuthResponse.builder()
                    .token(token)
                    .userId(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .roleId(user.getRole() != null ? user.getRole().getId() : 2)
                    .message("Login successful")
                    .build();
        } catch (Exception ex) {
            throw new RuntimeException("Login failed: " + ex.getMessage());
        }
    }
    
    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        // Delegate to RegistrationService for clean separation
        return registrationService.register(registerRequest);
    }
}
