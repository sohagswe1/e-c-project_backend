package com.example.sj.controller;

import com.example.sj.entity.Product;
import com.example.sj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(id, product));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
