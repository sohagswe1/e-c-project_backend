package com.example.sj.service;

import com.example.sj.entity.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Review save(Review review);
    Optional<Review> findById(Integer id);
    List<Review> findAll();
    Review update(Integer id, Review review);
    void delete(Integer id);
}
