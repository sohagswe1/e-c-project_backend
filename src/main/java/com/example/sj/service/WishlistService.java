package com.example.sj.service;

import com.example.sj.entity.Wishlist;
import java.util.List;
import java.util.Optional;

public interface WishlistService {
    Wishlist save(Wishlist wishlist);
    Optional<Wishlist> findById(Integer id);
    List<Wishlist> findAll();
    Wishlist update(Integer id, Wishlist wishlist);
    void delete(Integer id);
}
