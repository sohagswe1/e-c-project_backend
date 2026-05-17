package com.example.sj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error Response DTO
 * 
 * API error responses এর জন্য standardized format
 * এটি ensure করে যে response সবসময় valid JSON থাকে
 * 
 * @author Application Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int status;
    private String message;
    private String timestamp;
    private String path;
}
