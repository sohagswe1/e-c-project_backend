package com.example.sj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Integer roleId;
    
    // Customer-specific fields
    private String deliveryAddress;
    
    // Seller-specific fields
    private String shopName;
    private String tradeLicense;
    private String bankAccount;
    private String sellerPhone;
}
