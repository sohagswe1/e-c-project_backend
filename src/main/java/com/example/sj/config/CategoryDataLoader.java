package com.example.sj.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.sj.entity.Category;
import com.example.sj.repository.CategoryRepository;

/**
 * CategoryDataLoader - Initializes default categories on application startup
 * 
 * Creates standard product categories automatically if they don't exist
 * Categories: Shirt, Pant, Glass, Tie, Shari, Shoe, etc.
 * 
 * This runs only once when the application starts
 */
@Component
public class CategoryDataLoader implements CommandLineRunner {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public void run(String... args) throws Exception {
        try {
            categoryRepository.deleteAll();
            int createdCount = 0;
            for (Category.CategoryType categoryType : Category.CategoryType.values()) {
                String name = categoryType.getDisplayName();
                Category category = Category.builder()
                        .name(name)
                        .build();
                categoryRepository.save(category);
                createdCount++;
                System.out.println("   Created category: " + name);
            }

            System.out.println("✅ Category sync complete.");
            System.out.println("   Newly created: " + createdCount);
            System.out.println("   Total categories: " + categoryRepository.findAll().size());
        } catch (Exception e) {
            System.out.println("❌ Error loading categories: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
