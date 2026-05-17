package com.example.sj.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sj.entity.Admin;
import com.example.sj.entity.User;
import com.example.sj.repository.AdminRepository;
import com.example.sj.repository.UserRepository;
import com.example.sj.service.AdminService;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody Admin admin) {
        try {
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user by email
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Set user
            admin.setUser(user);
            
            return new ResponseEntity<>(adminService.save(admin), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error creating admin: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable Integer id) {
        try {
            Optional<Admin> admin = adminService.findById(id);
            if (admin.isPresent()) {
                return ResponseEntity.ok(admin);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Admin not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getCurrentUserAdmin() {
        try {
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user and their admin profile
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Optional<Admin> admin = adminRepository.findByUser(user);
            
            if (admin.isPresent()) {
                return ResponseEntity.ok(admin);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Admin profile not found for this user");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error fetching admin: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @RequestBody Admin admin) {
        try {
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Find existing admin
            Optional<Admin> existingAdmin = adminService.findById(id);
            if (!existingAdmin.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Admin not found");
            }
            
            // Check if current user owns this admin profile
            if (!existingAdmin.get().getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You cannot modify another admin's profile");
            }
            
            // Update admin
            return ResponseEntity.ok(adminService.update(id, admin));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error updating admin: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id) {
        try {
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Find existing admin
            Optional<Admin> existingAdmin = adminService.findById(id);
            if (!existingAdmin.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Admin not found");
            }
            
            // Check if current user owns this admin profile
            if (!existingAdmin.get().getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You cannot delete another admin's profile");
            }
            
            adminService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error deleting admin: " + e.getMessage());
        }
    }
}
