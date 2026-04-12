package com.example.sj.controller;

import com.example.sj.entity.SearchHistory;
import com.example.sj.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/search-history")
public class SearchHistoryController {
    
    @Autowired
    private SearchHistoryService searchHistoryService;
    
    @PostMapping
    public ResponseEntity<SearchHistory> createSearchHistory(@RequestBody SearchHistory searchHistory) {
        return new ResponseEntity<>(searchHistoryService.save(searchHistory), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<SearchHistory>> getSearchHistoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(searchHistoryService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<SearchHistory>> getAllSearchHistory() {
        return ResponseEntity.ok(searchHistoryService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SearchHistory> updateSearchHistory(@PathVariable Integer id, @RequestBody SearchHistory searchHistory) {
        return ResponseEntity.ok(searchHistoryService.update(id, searchHistory));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSearchHistory(@PathVariable Integer id) {
        searchHistoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
