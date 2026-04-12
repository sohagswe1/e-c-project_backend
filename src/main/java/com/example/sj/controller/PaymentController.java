package com.example.sj.controller;

import com.example.sj.entity.Payment;
import com.example.sj.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return new ResponseEntity<>(paymentService.save(payment), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Payment>> getPaymentById(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Integer id, @RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.update(id, payment));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
