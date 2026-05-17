package com.example.sj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.sj.service.FileStorageService;
import java.util.HashMap;
import java.util.Map;

/**
 * ImageUploadController
 * 
 * Handles image uploads for products
 * Uses FileStorageService to store images in upload/ folder
 * Images accessible via: http://localhost:8080/upload/filename.jpg
 * 
 * Note: This controller is kept for backward compatibility
 * Actual image upload endpoint is in ProductController: /api/products/upload-image
 */
@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageUploadController {
    
    @Autowired
    private FileStorageService fileStorageService;
    
    /**
     * Upload image
     * @param file Image file
     * @return filename and imageUrl
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "File is empty");
                return ResponseEntity.badRequest().body(error);
            }
            
            // Store file using FileStorageService
            String fileName = fileStorageService.storeFile(file);
            
            // Return response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("fileName", fileName);
            response.put("imageUrl", "http://localhost:8080/upload/" + fileName);
            response.put("message", "Image uploaded successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Upload failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
