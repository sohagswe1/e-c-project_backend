package com.example.sj.service;

import com.example.sj.entity.Seller;
import java.util.List;
import java.util.Optional;

public interface SellerService {
    Seller save(Seller seller);
    Optional<Seller> findById(Integer id);
    List<Seller> findAll();
    Seller update(Integer id, Seller seller);
    void delete(Integer id);
}
