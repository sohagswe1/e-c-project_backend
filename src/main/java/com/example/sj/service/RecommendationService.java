package com.example.sj.service;

import com.example.sj.entity.Recommendation;
import java.util.List;
import java.util.Optional;

public interface RecommendationService {
    Recommendation save(Recommendation recommendation);
    Optional<Recommendation> findById(Integer id);
    List<Recommendation> findAll();
    Recommendation update(Integer id, Recommendation recommendation);
    void delete(Integer id);
}
