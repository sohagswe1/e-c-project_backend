package com.example.sj.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.sj.entity.Admin;
import com.example.sj.entity.Role;
import com.example.sj.entity.User;
import com.example.sj.repository.AdminRepository;
import com.example.sj.repository.RoleRepository;
import com.example.sj.repository.UserRepository;

/**
 * AdminDataLoader - Initializes default admin user on application startup
 * 
 * Creates an admin account automatically if it doesn't exist
 * Email: sohag@gmail.com
 * Password: 12345678 (encoded with BCrypt)
 * 
 * This runs only once when the application starts
 */
@Component
public class AdminDataLoader implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        try {
            // Check if admin already exists
            if (userRepository.findByEmail("sohag@gmail.com").isEmpty()) {
                System.out.println("🔄 Creating default admin user...");
                
                // Get Admin role (ID: 3)
                Role adminRole = roleRepository.findById(3)
                        .orElseThrow(() -> new RuntimeException("Admin role not found. Please ensure Role with ID 3 exists"));
                
                // Create new admin user
                User adminUser = User.builder()
                        .name("Platform Admin")
                        .email("sohag@gmail.com")
                        .password(passwordEncoder.encode("12345678"))
                        .phone("01700000000")
                        .role(adminRole)
                        .createdAt(LocalDateTime.now())
                        .build();
                
                // Save admin user
                User savedAdmin = userRepository.save(adminUser);
                
                // Create Admin record
                Admin admin = Admin.builder()
                        .user(savedAdmin)
                        .permissions("ALL")
                        .build();
                
                adminRepository.save(admin);
                
                System.out.println("✅ Default admin created successfully!");
                System.out.println("   Email: sohag@gmail.com");
                System.out.println("   Password: 12345678");
                System.out.println("   Role: Admin (ID: 3)");
                
            } else {
                System.out.println("✅ Admin user already exists. Skipping creation.");
            }
            
        } catch (Exception ex) {
            System.out.println("❌ Error creating admin user: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
