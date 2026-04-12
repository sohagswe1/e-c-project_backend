package com.example.sj.service_implement;

import com.example.sj.entity.Review;
import com.example.sj.repository.ReviewRepository;
import com.example.sj.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImplement implements ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }
    
    @Override
    public Optional<Review> findById(Integer id) {
        return reviewRepository.findById(id);
    }
    
    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
    
    @Override
    public Review update(Integer id, Review review) {
        return reviewRepository.findById(id).map(existingReview -> {
            Review updatedReview = Review.builder()
                    .id(existingReview.getId())
                    .user(review.getUser())
                    .product(review.getProduct())
                    .rating(review.getRating())
                    .comment(review.getComment())
                    .createdAt(review.getCreatedAt())
                    .build();
            return reviewRepository.save(updatedReview);
        }).orElseThrow(() -> new RuntimeException("Review not found"));
    }
    
    @Override
    public void delete(Integer id) {
        reviewRepository.deleteById(id);
    }
}
