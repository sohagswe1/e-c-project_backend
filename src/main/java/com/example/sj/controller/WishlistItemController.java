package com.example.sj.controller;

import com.example.sj.entity.WishlistItem;
import com.example.sj.entity.Wishlist;
import com.example.sj.entity.Product;
import com.example.sj.entity.User;
import com.example.sj.repository.WishlistItemRepository;
import com.example.sj.repository.WishlistRepository;
import com.example.sj.repository.ProductRepository;
import com.example.sj.repository.UserRepository;
import com.example.sj.service.WishlistItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishlist-items")
public class WishlistItemController {
    
    @Autowired
    private WishlistItemService wishlistItemService;

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    
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

    @GetMapping("/my")
    public ResponseEntity<?> getMyWishlistItems() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

            List<Map<String, Object>> items = wishlistItemRepository.findByWishlistUserId(user.getId()).stream().map(item -> {
                Map<String, Object> row = new HashMap<>();
                row.put("id", item.getId());
                row.put("productId", item.getProduct().getId());
                row.put("name", item.getProduct().getName());
                row.put("price", item.getProduct().getPrice());
                row.put("image", item.getProduct().getImage());
                return row;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/my/{productId}")
    public ResponseEntity<?> addToMyWishlist(@PathVariable Integer productId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
            Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

            Wishlist wishlist = wishlistRepository.findByUserId(user.getId()).orElseGet(() -> wishlistRepository.save(Wishlist.builder().user(user).build()));
            Optional<WishlistItem> existing = wishlistItemRepository.findByWishlistUserIdAndProductId(user.getId(), productId);
            if (existing.isEmpty()) {
                wishlistItemRepository.save(WishlistItem.builder().wishlist(wishlist).product(product).build());
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Added to wishlist"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/my/{productId}")
    public ResponseEntity<?> removeFromMyWishlist(@PathVariable Integer productId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

            Optional<WishlistItem> existing = wishlistItemRepository.findByWishlistUserIdAndProductId(user.getId(), productId);
            if (existing.isPresent()) {
                wishlistItemRepository.delete(existing.get());
            }

            return ResponseEntity.ok(Map.of("message", "Removed from wishlist"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
