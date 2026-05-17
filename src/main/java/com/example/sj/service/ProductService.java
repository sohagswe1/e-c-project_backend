package com.example.sj.service;

import com.example.sj.dto.ProductDTO;
import com.example.sj.entity.Category;
import com.example.sj.entity.Product;
import com.example.sj.entity.Seller;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Integer id);
    List<Product> findAll();
    List<Product> findBySeller(Seller seller);
    Product saveFromDTO(ProductDTO productDTO, Seller seller, Category category);
    Product updateFromDTO(Integer id, ProductDTO productDTO);
    void delete(Integer id);
}
