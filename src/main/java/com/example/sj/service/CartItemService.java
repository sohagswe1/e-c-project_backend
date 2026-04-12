package com.example.sj.service;

import com.example.sj.entity.CartItem;
import java.util.List;
import java.util.Optional;

public interface CartItemService {
    CartItem save(CartItem cartItem);
    Optional<CartItem> findById(Integer id);
    List<CartItem> findAll();
    CartItem update(Integer id, CartItem cartItem);
    void delete(Integer id);
}
