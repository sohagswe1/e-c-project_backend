package com.example.sj.service;

import com.example.sj.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category save(Category category);
    Optional<Category> findById(Integer id);
    List<Category> findAll();
    Category update(Integer id, Category category);
    void delete(Integer id);
}
