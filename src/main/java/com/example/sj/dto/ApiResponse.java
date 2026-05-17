package com.example.sj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API Response DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
    private String message;
    private Object data;
    
    public ApiResponse(String message) {
        this.message = message;
        this.data = null;
    }
}
