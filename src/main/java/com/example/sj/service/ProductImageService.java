package com.example.sj.service;

import com.example.sj.entity.ProductImage;
import java.util.List;
import java.util.Optional;

public interface ProductImageService {
    ProductImage save(ProductImage productImage);
    Optional<ProductImage> findById(Integer id);
    List<ProductImage> findAll();
    ProductImage update(Integer id, ProductImage productImage);
    void delete(Integer id);
}
