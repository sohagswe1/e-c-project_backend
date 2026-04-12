package com.example.sj.service_implement;

import com.example.sj.entity.ProductImage;
import com.example.sj.repository.ProductImageRepository;
import com.example.sj.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductImageServiceImplement implements ProductImageService {
    
    @Autowired
    private ProductImageRepository productImageRepository;
    
    @Override
    public ProductImage save(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }
    
    @Override
    public Optional<ProductImage> findById(Integer id) {
        return productImageRepository.findById(id);
    }
    
    @Override
    public List<ProductImage> findAll() {
        return productImageRepository.findAll();
    }
    
    @Override
    public ProductImage update(Integer id, ProductImage productImage) {
        return productImageRepository.findById(id).map(existingProductImage -> {
            ProductImage updatedProductImage = ProductImage.builder()
                    .id(existingProductImage.getId())
                    .product(productImage.getProduct())
                    .imageUrl(productImage.getImageUrl())
                    .build();
            return productImageRepository.save(updatedProductImage);
        }).orElseThrow(() -> new RuntimeException("ProductImage not found"));
    }
    
    @Override
    public void delete(Integer id) {
        productImageRepository.deleteById(id);
    }
}
