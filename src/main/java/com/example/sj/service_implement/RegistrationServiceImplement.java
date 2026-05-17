package com.example.sj.service_implement;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sj.dto.AuthResponse;
import com.example.sj.dto.RegisterRequest;
import com.example.sj.entity.Customer;
import com.example.sj.entity.Role;
import com.example.sj.entity.Seller;
import com.example.sj.entity.User;
import com.example.sj.repository.CustomerRepository;
import com.example.sj.repository.RoleRepository;
import com.example.sj.repository.SellerRepository;
import com.example.sj.repository.UserRepository;
import com.example.sj.security.JwtTokenProvider;
import com.example.sj.service.RegistrationService;

/**
 * Registration Service - Handle user registration with role-specific profile creation
 * 
 * Flow:
 * ১. মেইন User Entity তৈরি ও সেভ করা
 * ২. রোল অনুযায়ী বাকি ডাটা সেভ করা:
 *    - SELLER → Seller প্রোফাইল (shopName, tradeLicense, phone, bankAccount)
 *    - CUSTOMER → Customer প্রোফাইল (deliveryAddress, phone, loyaltyPoints)
 * 
 * @author Application Team
 * @version 1.0
 */
@Service
public class RegistrationServiceImplement implements RegistrationService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private SellerRepository sellerRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        try {
            // ১. Check if email already exists
            if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                throw new RuntimeException("Email already registered");
            }
            
            // ২. Get role
            Integer roleId = registerRequest.getRoleId() != null ? registerRequest.getRoleId() : 2;
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            
            // ३. Create Main User Entity
            User user = User.builder()
                    .name(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .phone(registerRequest.getPhone())
                    .address(registerRequest.getAddress())
                    .role(role)
                    .createdAt(LocalDateTime.now())
                    .build();
            
            // Save user
            User savedUser = userRepository.save(user);
            System.out.println("✓ User created: " + savedUser.getEmail() + " (ID: " + savedUser.getId() + ")");
            
            // ४. Create role-specific profile
            if (roleId == 1) {
                // SELLER PROFILE
                createSellerProfile(savedUser, registerRequest);
            } else if (roleId == 2) {
                // CUSTOMER PROFILE
                createCustomerProfile(savedUser, registerRequest);
            }
            
            // ५. Generate JWT Token
            String token = jwtTokenProvider.generateTokenFromEmail(savedUser.getEmail());
            
            // ६. Return success response
            return AuthResponse.builder()
                    .token(token)
                    .userId(savedUser.getId())
                    .email(savedUser.getEmail())
                    .name(savedUser.getName())
                    .roleId(roleId)
                    .message("User registered successfully")
                    .build();
            
        } catch (Exception e) {
            System.out.println("✗ Registration failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }
    
    /**
     * Create Seller Profile with seller-specific data
     */
    private void createSellerProfile(User user, RegisterRequest request) {
        Seller seller = Seller.builder()
                .user(user)
                .shopName(request.getShopName() != null ? request.getShopName() : "My Shop")
                .tradeLicense(request.getTradeLicense() != null ? request.getTradeLicense() : "TL-" + user.getId())
                .phone(request.getSellerPhone() != null ? request.getSellerPhone() : user.getPhone())
                .bankAccount(request.getBankAccount() != null ? request.getBankAccount() : "BANK-" + user.getId())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        
        Seller savedSeller = sellerRepository.save(seller);
        System.out.println("✓ Seller Profile created: " + savedSeller.getId());
    }
    
    /**
     * Create Customer Profile with customer-specific data
     */
    private void createCustomerProfile(User user, RegisterRequest request) {
        Customer customer = Customer.builder()
                .user(user)
                .loyaltyPoints(0)
                .deliveryAddress(request.getDeliveryAddress() != null ? request.getDeliveryAddress() : user.getAddress())
                .phone(user.getPhone())
                .build();
        
        Customer savedCustomer = customerRepository.save(customer);
        System.out.println("✓ Customer Profile created: " + savedCustomer.getId());
    }
}
