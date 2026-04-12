package com.example.sj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "seller_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;
    
    private String status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
