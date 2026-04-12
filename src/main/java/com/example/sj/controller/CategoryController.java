package com.example.sj.controller;

import com.example.sj.entity.Category;
import com.example.sj.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.update(id, category));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
