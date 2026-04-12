package com.example.sj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
   @Builder.Default
    private String type = "Bearer";
    private Integer userId;
    private String email;
    private String name;
    private String message;
    
    public AuthResponse(String token, Integer userId, String email, String name) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.name = name;
    }
}
