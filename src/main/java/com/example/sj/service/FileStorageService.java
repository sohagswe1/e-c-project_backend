package com.example.sj.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

/**
 * File Storage Service - Handle image upload to project folder
 * 
 * Images will be stored in project_root/upload/ folder
 * Access via: http://localhost:8080/upload/filename.jpg
 */
@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            // ফোল্ডার না থাকলে তৈরি করবে
            Files.createDirectories(this.fileStorageLocation);
            System.out.println("✓ Upload directory created at: " + this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create upload directory", ex);
        }
    }

    /**
     * Store file in upload folder
     * @param file - MultipartFile from request
     * @return fileName - Unique filename for database storage
     */
    public String storeFile(MultipartFile file) {
        try {
            // Validate file
            if (file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }

            // Validate file type (only images)
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("File must be an image (jpeg, png, jpg, gif, webp)");
            }

            // Create unique filename
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + extension;
            
            // Create target path
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            
            // Copy file to folder
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            System.out.println("✓ File uploaded: " + fileName);
            return fileName; // Return filename for database storage
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file. Error: " + ex.getMessage());
        }
    }

    /**
     * Delete file from upload folder
     */
    public void deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("✓ File deleted: " + fileName);
            }
        } catch (IOException ex) {
            System.out.println("✗ Could not delete file: " + ex.getMessage());
        }
    }

    /**
     * Get upload directory path
     */
    public String getUploadDir() {
        return this.fileStorageLocation.toString();
    }
}
