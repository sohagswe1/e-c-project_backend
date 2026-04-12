package com.example.sj.service_implement;

import com.example.sj.entity.SearchHistory;
import com.example.sj.repository.SearchHistoryRepository;
import com.example.sj.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SearchHistoryServiceImplement implements SearchHistoryService {
    
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;
    
    @Override
    public SearchHistory save(SearchHistory searchHistory) {
        return searchHistoryRepository.save(searchHistory);
    }
    
    @Override
    public Optional<SearchHistory> findById(Integer id) {
        return searchHistoryRepository.findById(id);
    }
    
    @Override
    public List<SearchHistory> findAll() {
        return searchHistoryRepository.findAll();
    }
    
    @Override
    public SearchHistory update(Integer id, SearchHistory searchHistory) {
        return searchHistoryRepository.findById(id).map(existingSearchHistory -> {
            SearchHistory updatedSearchHistory = SearchHistory.builder()
                    .id(existingSearchHistory.getId())
                    .user(searchHistory.getUser())
                    .query(searchHistory.getQuery())
                    .createdAt(searchHistory.getCreatedAt())
                    .build();
            return searchHistoryRepository.save(updatedSearchHistory);
        }).orElseThrow(() -> new RuntimeException("SearchHistory not found"));
    }
    
    @Override
    public void delete(Integer id) {
        searchHistoryRepository.deleteById(id);
    }
}
