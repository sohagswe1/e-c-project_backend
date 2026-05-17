package com.example.sj.controller;

import java.time.LocalDateTime;
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

import com.example.sj.entity.Seller;
import com.example.sj.entity.User;
import com.example.sj.repository.SellerRepository;
import com.example.sj.repository.UserRepository;
import com.example.sj.service.SellerService;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {
    
    @Autowired
    private SellerService sellerService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SellerRepository sellerRepository;
    
    @PostMapping
    public ResponseEntity<?> createSeller(@RequestBody Seller seller) {
        try {
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user by email
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Set user and created date
            seller.setUser(user);
            seller.setCreatedAt(LocalDateTime.now());
            
            return new ResponseEntity<>(sellerService.save(seller), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error creating seller: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getSellerById(@PathVariable Integer id) {
        try {
            Optional<Seller> seller = sellerService.findById(id);
            if (seller.isPresent()) {
                return ResponseEntity.ok(seller);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Seller not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getCurrentUserSeller() {
        try {
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user and their seller profile
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Optional<Seller> seller = sellerRepository.findByUser(user);
            
            if (seller.isPresent()) {
                return ResponseEntity.ok(seller);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Seller profile not found for this user");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error fetching seller: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeller(@PathVariable Integer id, @RequestBody Seller seller) {
        try {
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Find existing seller
            Optional<Seller> existingSeller = sellerService.findById(id);
            if (!existingSeller.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Seller not found");
            }
            
            // Check if current user owns this seller profile
            if (!existingSeller.get().getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You cannot modify another seller's profile");
            }
            
            // Update seller
            return ResponseEntity.ok(sellerService.update(id, seller));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error updating seller: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeller(@PathVariable Integer id) {
        try {
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Find existing seller
            Optional<Seller> existingSeller = sellerService.findById(id);
            if (!existingSeller.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Seller not found");
            }
            
            // Check if current user owns this seller profile
            if (!existingSeller.get().getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You cannot delete another seller's profile");
            }
            
            sellerService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error deleting seller: " + e.getMessage());
        }
    }
}
