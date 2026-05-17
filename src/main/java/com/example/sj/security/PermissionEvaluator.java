package com.example.sj.security;

import com.example.sj.entity.Admin;
import com.example.sj.entity.User;
import com.example.sj.repository.AdminRepository;
import com.example.sj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Custom Permission Evaluator for @PreAuthorized annotations
 * 
 * এই class @PreAuthorized annotation এ custom permission evaluate করে
 * 
 * Usage in Controller:
 *   @PreAuthorized("@permissionEvaluator.isAdmin()")
 *   public ResponseEntity<?> updateRole(...) { ... }
 * 
 * @author Application Team
 * @version 1.0
 */
@Component
public class PermissionEvaluator {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdminRepository adminRepository;
    
    /**
     * Check if current authenticated user is ADMIN
     * 
     * @return true if user has admin profile, false otherwise
     */
    public boolean isAdmin() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            // Check if authenticated
            if (authentication == null || !authentication.isAuthenticated()) {
                return false;
            }
            
            String currentUserEmail = authentication.getName();
            
            // Find user by email
            Optional<User> user = userRepository.findByEmail(currentUserEmail);
            if (!user.isPresent()) {
                return false;
            }
            
            // Check if user has admin profile
            Optional<Admin> admin = adminRepository.findByUser(user.get());
            return admin.isPresent();
            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if current user owns a specific user profile
     * 
     * @param userId The user ID to check ownership
     * @return true if current user matches the given userId
     */
    public boolean isOwner(Integer userId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated()) {
                return false;
            }
            
            String currentUserEmail = authentication.getName();
            Optional<User> user = userRepository.findByEmail(currentUserEmail);
            
            if (!user.isPresent()) {
                return false;
            }
            
            return user.get().getId().equals(userId);
            
        } catch (Exception e) {
            return false;
        }
    }
}
