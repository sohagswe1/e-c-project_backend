package com.example.sj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "sellers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "shop_name")
    private String shopName;
    
    @Column(name = "trade_license")
    private String tradeLicense;
    
    @Column(name = "bank_account")
    private String bankAccount;
    
    private String status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
