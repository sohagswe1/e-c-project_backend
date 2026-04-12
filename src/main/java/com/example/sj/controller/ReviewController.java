package com.example.sj.controller;

import com.example.sj.entity.Review;
import com.example.sj.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        return new ResponseEntity<>(reviewService.save(review), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Review>> getReviewById(@PathVariable Integer id) {
        return ResponseEntity.ok(reviewService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Integer id, @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.update(id, review));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
