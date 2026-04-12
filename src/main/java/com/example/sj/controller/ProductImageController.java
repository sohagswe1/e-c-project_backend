package com.example.sj.controller;

import com.example.sj.entity.ProductImage;
import com.example.sj.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-images")
public class ProductImageController {
    
    @Autowired
    private ProductImageService productImageService;
    
    @PostMapping
    public ResponseEntity<ProductImage> createProductImage(@RequestBody ProductImage productImage) {
        return new ResponseEntity<>(productImageService.save(productImage), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductImage>> getProductImageById(@PathVariable Integer id) {
        return ResponseEntity.ok(productImageService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<ProductImage>> getAllProductImages() {
        return ResponseEntity.ok(productImageService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductImage> updateProductImage(@PathVariable Integer id, @RequestBody ProductImage productImage) {
        return ResponseEntity.ok(productImageService.update(id, productImage));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Integer id) {
        productImageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
