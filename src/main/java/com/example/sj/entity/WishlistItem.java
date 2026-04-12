package com.example.sj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wishlist_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
