package com.example.sj.controller;

import com.example.sj.entity.SellerOrder;
import com.example.sj.service.SellerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seller-orders")
public class SellerOrderController {
    
    @Autowired
    private SellerOrderService sellerOrderService;
    
    @PostMapping
    public ResponseEntity<SellerOrder> createSellerOrder(@RequestBody SellerOrder sellerOrder) {
        return new ResponseEntity<>(sellerOrderService.save(sellerOrder), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<SellerOrder>> getSellerOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(sellerOrderService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<SellerOrder>> getAllSellerOrders() {
        return ResponseEntity.ok(sellerOrderService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SellerOrder> updateSellerOrder(@PathVariable Integer id, @RequestBody SellerOrder sellerOrder) {
        return ResponseEntity.ok(sellerOrderService.update(id, sellerOrder));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSellerOrder(@PathVariable Integer id) {
        sellerOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
