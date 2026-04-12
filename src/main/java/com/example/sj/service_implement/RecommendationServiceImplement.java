package com.example.sj.service_implement;

import com.example.sj.entity.Recommendation;
import com.example.sj.repository.RecommendationRepository;
import com.example.sj.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RecommendationServiceImplement implements RecommendationService {
    
    @Autowired
    private RecommendationRepository recommendationRepository;
    
    @Override
    public Recommendation save(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }
    
    @Override
    public Optional<Recommendation> findById(Integer id) {
        return recommendationRepository.findById(id);
    }
    
    @Override
    public List<Recommendation> findAll() {
        return recommendationRepository.findAll();
    }
    
    @Override
    public Recommendation update(Integer id, Recommendation recommendation) {
        return recommendationRepository.findById(id).map(existingRecommendation -> {
            Recommendation updatedRecommendation = Recommendation.builder()
                    .id(existingRecommendation.getId())
                    .product(recommendation.getProduct())
                    .user(recommendation.getUser())
                    .build();
            return recommendationRepository.save(updatedRecommendation);
        }).orElseThrow(() -> new RuntimeException("Recommendation not found"));
    }
    
    @Override
    public void delete(Integer id) {
        recommendationRepository.deleteById(id);
    }
}
