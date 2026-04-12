package com.example.sj.service;

import com.example.sj.entity.SearchHistory;
import java.util.List;
import java.util.Optional;

public interface SearchHistoryService {
    SearchHistory save(SearchHistory searchHistory);
    Optional<SearchHistory> findById(Integer id);
    List<SearchHistory> findAll();
    SearchHistory update(Integer id, SearchHistory searchHistory);
    void delete(Integer id);
}
