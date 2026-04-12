package com.example.sj.service_implement;

import com.example.sj.entity.Product;
import com.example.sj.repository.ProductRepository;
import com.example.sj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplement implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
    
    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }
    
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    @Override
    public Product update(Integer id, Product product) {
        return productRepository.findById(id).map(existingProduct -> {
            Product updatedProduct = Product.builder()
                    .id(existingProduct.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .stock(product.getStock())
                    .build();
            return productRepository.save(updatedProduct);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }
    
    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
