package com.example.sj.controller;

import com.example.sj.entity.WishlistItem;
import com.example.sj.service.WishlistItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wishlist-items")
public class WishlistItemController {
    
    @Autowired
    private WishlistItemService wishlistItemService;
    
    @PostMapping
    public ResponseEntity<WishlistItem> createWishlistItem(@RequestBody WishlistItem wishlistItem) {
        return new ResponseEntity<>(wishlistItemService.save(wishlistItem), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<WishlistItem>> getWishlistItemById(@PathVariable Integer id) {
        return ResponseEntity.ok(wishlistItemService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<WishlistItem>> getAllWishlistItems() {
        return ResponseEntity.ok(wishlistItemService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<WishlistItem> updateWishlistItem(@PathVariable Integer id, @RequestBody WishlistItem wishlistItem) {
        return ResponseEntity.ok(wishlistItemService.update(id, wishlistItem));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlistItem(@PathVariable Integer id) {
        wishlistItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
