package com.example.sj.controller;

import com.example.sj.entity.Seller;
import com.example.sj.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {
    
    @Autowired
    private SellerService sellerService;
    
    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) {
        return new ResponseEntity<>(sellerService.save(seller), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Seller>> getSellerById(@PathVariable Integer id) {
        return ResponseEntity.ok(sellerService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers() {
        return ResponseEntity.ok(sellerService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable Integer id, @RequestBody Seller seller) {
        return ResponseEntity.ok(sellerService.update(id, seller));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Integer id) {
        sellerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
