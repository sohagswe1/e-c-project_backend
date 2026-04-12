package com.example.sj.service;

import com.example.sj.entity.SellerOrder;
import java.util.List;
import java.util.Optional;

public interface SellerOrderService {
    SellerOrder save(SellerOrder sellerOrder);
    Optional<SellerOrder> findById(Integer id);
    List<SellerOrder> findAll();
    SellerOrder update(Integer id, SellerOrder sellerOrder);
    void delete(Integer id);
}
