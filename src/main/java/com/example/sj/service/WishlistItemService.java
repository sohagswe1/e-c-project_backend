package com.example.sj.service;

import com.example.sj.entity.WishlistItem;
import java.util.List;
import java.util.Optional;

public interface WishlistItemService {
    WishlistItem save(WishlistItem wishlistItem);
    Optional<WishlistItem> findById(Integer id);
    List<WishlistItem> findAll();
    WishlistItem update(Integer id, WishlistItem wishlistItem);
    void delete(Integer id);
}
