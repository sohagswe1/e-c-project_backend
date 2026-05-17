package com.example.sj.controller;

import com.example.sj.entity.CartItem;
import com.example.sj.entity.Cart;
import com.example.sj.entity.Product;
import com.example.sj.entity.User;
import com.example.sj.repository.CartItemRepository;
import com.example.sj.repository.CartRepository;
import com.example.sj.repository.ProductRepository;
import com.example.sj.repository.UserRepository;
import com.example.sj.service.CartItemService;
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
@RequestMapping("/api/cart-items")
public class CartItemController {
    
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    
    @PostMapping
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) {
        return new ResponseEntity<>(cartItemService.save(cartItem), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<CartItem>> getCartItemById(@PathVariable Integer id) {
        return ResponseEntity.ok(cartItemService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Integer id, @RequestBody CartItem cartItem) {
        return ResponseEntity.ok(cartItemService.update(id, cartItem));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Integer id) {
        cartItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyCartItems() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

            List<Map<String, Object>> items = cartItemRepository.findByCartUserId(user.getId()).stream().map(item -> {
                Map<String, Object> row = new HashMap<>();
                row.put("id", item.getId());
                row.put("productId", item.getProduct().getId());
                row.put("name", item.getProduct().getName());
                row.put("price", item.getProduct().getPrice());
                row.put("image", item.getProduct().getImage());
                row.put("quantity", item.getQuantity());
                return row;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/my/{productId}")
    public ResponseEntity<?> addToMyCart(@PathVariable Integer productId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
            Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

            Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> cartRepository.save(Cart.builder().user(user).build()));
            Optional<CartItem> existing = cartItemRepository.findByCartUserIdAndProductId(user.getId(), productId);

            if (existing.isPresent()) {
                CartItem item = existing.get();
                item.setQuantity((item.getQuantity() == null ? 0 : item.getQuantity()) + 1);
                cartItemRepository.save(item);
            } else {
                cartItemRepository.save(CartItem.builder().cart(cart).product(product).quantity(1).build());
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Added to cart"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/my/{productId}")
    public ResponseEntity<?> removeFromMyCart(@PathVariable Integer productId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

            Optional<CartItem> existing = cartItemRepository.findByCartUserIdAndProductId(user.getId(), productId);
            if (existing.isPresent()) {
                cartItemRepository.delete(existing.get());
            }

            return ResponseEntity.ok(Map.of("message", "Removed from cart"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
