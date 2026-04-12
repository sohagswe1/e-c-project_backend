package com.example.sj.controller;

import com.example.sj.entity.Cart;
import com.example.sj.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        return new ResponseEntity<>(cartService.save(cart), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cart>> getCartById(@PathVariable Integer id) {
        return ResponseEntity.ok(cartService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        return ResponseEntity.ok(cartService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Integer id, @RequestBody Cart cart) {
        return ResponseEntity.ok(cartService.update(id, cart));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Integer id) {
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
