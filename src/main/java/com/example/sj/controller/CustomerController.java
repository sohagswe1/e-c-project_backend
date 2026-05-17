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

import com.example.sj.entity.Customer;
import com.example.sj.entity.User;
import com.example.sj.repository.CustomerRepository;
import com.example.sj.repository.UserRepository;
import com.example.sj.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user by email
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Set user
            customer.setUser(user);
            
            return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error creating customer: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        try {
            Optional<Customer> customer = customerService.findById(id);
            if (customer.isPresent()) {
                return ResponseEntity.ok(customer);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getCurrentUserCustomer() {
        try {
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user and their customer profile
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Optional<Customer> customer = customerRepository.findByUser(user);
            
            if (customer.isPresent()) {
                return ResponseEntity.ok(customer);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Customer profile not found for this user");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error fetching customer: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        try {
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Find existing customer
            Optional<Customer> existingCustomer = customerService.findById(id);
            if (!existingCustomer.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Customer not found");
            }
            
            // Check if current user owns this customer profile
            if (!existingCustomer.get().getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You cannot modify another customer's profile");
            }
            
            // Update customer
            return ResponseEntity.ok(customerService.update(id, customer));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error updating customer: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        try {
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Find existing customer
            Optional<Customer> existingCustomer = customerService.findById(id);
            if (!existingCustomer.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Customer not found");
            }
            
            // Check if current user owns this customer profile
            if (!existingCustomer.get().getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You cannot delete another customer's profile");
            }
            
            customerService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error deleting customer: " + e.getMessage());
        }
    }
}
