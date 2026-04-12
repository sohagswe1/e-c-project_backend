package com.example.sj.service;

import com.example.sj.entity.Cart;
import java.util.List;
import java.util.Optional;

public interface CartService {
    Cart save(Cart cart);
    Optional<Cart> findById(Integer id);
    List<Cart> findAll();
    Cart update(Integer id, Cart cart);
    void delete(Integer id);
}
