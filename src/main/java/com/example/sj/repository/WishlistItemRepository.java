package com.example.sj.repository;

import com.example.sj.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Integer> {
    List<WishlistItem> findByWishlistUserId(Integer userId);
    Optional<WishlistItem> findByWishlistUserIdAndProductId(Integer userId, Integer productId);
}
