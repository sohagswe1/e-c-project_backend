package com.example.sj.controller;

import com.example.sj.entity.Order;
import com.example.sj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.save(order), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Order>> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        return ResponseEntity.ok(orderService.update(id, order));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
