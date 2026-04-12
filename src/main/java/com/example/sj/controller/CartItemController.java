package com.example.sj.controller;

import com.example.sj.entity.CartItem;
import com.example.sj.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {
    
    @Autowired
    private CartItemService cartItemService;
    
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
}
