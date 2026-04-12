package com.example.sj.controller;

import com.example.sj.entity.Recommendation;
import com.example.sj.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    
    @Autowired
    private RecommendationService recommendationService;
    
    @PostMapping
    public ResponseEntity<Recommendation> createRecommendation(@RequestBody Recommendation recommendation) {
        return new ResponseEntity<>(recommendationService.save(recommendation), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Recommendation>> getRecommendationById(@PathVariable Integer id) {
        return ResponseEntity.ok(recommendationService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Recommendation>> getAllRecommendations() {
        return ResponseEntity.ok(recommendationService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Recommendation> updateRecommendation(@PathVariable Integer id, @RequestBody Recommendation recommendation) {
        return ResponseEntity.ok(recommendationService.update(id, recommendation));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecommendation(@PathVariable Integer id) {
        recommendationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
