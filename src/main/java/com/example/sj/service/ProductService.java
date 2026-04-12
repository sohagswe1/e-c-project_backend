package com.example.sj.service;

import com.example.sj.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);
    Optional<Product> findById(Integer id);
    List<Product> findAll();
    Product update(Integer id, Product product);
    void delete(Integer id);
}
