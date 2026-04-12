package com.example.sj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Column(name = "image_url")
    private String imageUrl;
}
