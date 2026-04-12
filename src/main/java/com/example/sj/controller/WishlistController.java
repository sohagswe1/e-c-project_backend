package com.example.sj.controller;

import com.example.sj.entity.Wishlist;
import com.example.sj.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {
    
    @Autowired
    private WishlistService wishlistService;
    
    @PostMapping
    public ResponseEntity<Wishlist> createWishlist(@RequestBody Wishlist wishlist) {
        return new ResponseEntity<>(wishlistService.save(wishlist), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Wishlist>> getWishlistById(@PathVariable Integer id) {
        return ResponseEntity.ok(wishlistService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Wishlist>> getAllWishlists() {
        return ResponseEntity.ok(wishlistService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Wishlist> updateWishlist(@PathVariable Integer id, @RequestBody Wishlist wishlist) {
        return ResponseEntity.ok(wishlistService.update(id, wishlist));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Integer id) {
        wishlistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
