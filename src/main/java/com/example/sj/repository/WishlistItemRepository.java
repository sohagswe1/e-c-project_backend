package com.example.sj.repository;

import com.example.sj.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Integer> {
}
