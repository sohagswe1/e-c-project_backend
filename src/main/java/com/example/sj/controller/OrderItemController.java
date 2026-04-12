package com.example.sj.controller;

import com.example.sj.entity.OrderItem;
import com.example.sj.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    
    @Autowired
    private OrderItemService orderItemService;
    
    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
        return new ResponseEntity<>(orderItemService.save(orderItem), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<OrderItem>> getOrderItemById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderItemService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Integer id, @RequestBody OrderItem orderItem) {
        return ResponseEntity.ok(orderItemService.update(id, orderItem));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Integer id) {
        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
