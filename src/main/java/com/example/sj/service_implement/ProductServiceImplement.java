package com.example.sj.service_implement;

import com.example.sj.dto.ProductDTO;
import com.example.sj.entity.Category;
import com.example.sj.entity.Product;
import com.example.sj.entity.Seller;
import com.example.sj.repository.ProductRepository;
import com.example.sj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplement implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }
    
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    @Override
    public List<Product> findBySeller(Seller seller) {
        return productRepository.findBySeller(seller);
    }
    
    /**
     * DTO থেকে Product Entity তৈরি করে save করা
     */
    @Override
    public Product saveFromDTO(ProductDTO productDTO, Seller seller, Category category) {
        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock() != null ? productDTO.getStock() : 0)
                .image(productDTO.getImage())
                .seller(seller)
                .category(category)
                .createdAt(LocalDateTime.now())
                .build();
        
        return productRepository.save(product);
    }
    
    /**
     * DTO থেকে Product Entity update করা
     */
    @Override
    public Product updateFromDTO(Integer id, ProductDTO productDTO) {
        return productRepository.findById(id).map(existingProduct -> {
            // DTO থেকে Entity update করা
            if (productDTO.getName() != null) {
                existingProduct.setName(productDTO.getName());
            }
            if (productDTO.getDescription() != null) {
                existingProduct.setDescription(productDTO.getDescription());
            }
            if (productDTO.getPrice() != null) {
                existingProduct.setPrice(productDTO.getPrice());
            }
            if (productDTO.getStock() != null) {
                existingProduct.setStock(productDTO.getStock());
            }
            if (productDTO.getImage() != null) {
                existingProduct.setImage(productDTO.getImage());
            }
            
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }
    
    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
