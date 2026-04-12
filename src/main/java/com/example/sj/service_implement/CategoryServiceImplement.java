package com.example.sj.service_implement;

import com.example.sj.entity.Category;
import com.example.sj.repository.CategoryRepository;
import com.example.sj.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplement implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
    
    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }
    
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    @Override
    public Category update(Integer id, Category category) {
        return categoryRepository.findById(id).map(existingCategory -> {
            Category updatedCategory = Category.builder()
                    .id(existingCategory.getId())
                    .name(category.getName())
                    .build();
            return categoryRepository.save(updatedCategory);
        }).orElseThrow(() -> new RuntimeException("Category not found"));
    }
    
    @Override
    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }
}
