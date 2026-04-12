package com.example.sj.service_implement;

import com.example.sj.dto.LoginRequest;
import com.example.sj.dto.AuthResponse;
import com.example.sj.dto.RegisterRequest;
import com.example.sj.entity.User;
import com.example.sj.entity.Role;
import com.example.sj.repository.UserRepository;
import com.example.sj.repository.RoleRepository;
import com.example.sj.security.JwtTokenProvider;
import com.example.sj.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthServiceImplement implements AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
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
                    .message("Login successful")
                    .build();
        } catch (Exception ex) {
            throw new RuntimeException("Login failed: " + ex.getMessage());
        }
    }
    
    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        try {
            // Check if user already exists
            if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                throw new RuntimeException("Email already registered");
            }
            
            // Get role or create default
            Role role = roleRepository.findById(registerRequest.getRoleId() != null ? registerRequest.getRoleId() : 3)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            
            // Create new user using builder
            User user = User.builder()
                    .name(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .phone(registerRequest.getPhone())
                    .role(role)
                    .createdAt(LocalDateTime.now())
                    .build();
            
            // Save user
            User savedUser = userRepository.save(user);
            
            // Generate token
            String token = jwtTokenProvider.generateTokenFromEmail(savedUser.getEmail());
            
            // Return response using builder
            return AuthResponse.builder()
                    .token(token)
                    .userId(savedUser.getId())
                    .email(savedUser.getEmail())
                    .name(savedUser.getName())
                    .message("User registered successfully")
                    .build();
        } catch (Exception ex) {
            throw new RuntimeException("Registration failed: " + ex.getMessage());
        }
    }
}
