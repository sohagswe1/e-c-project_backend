package com.example.sj.repository;

import com.example.sj.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCartUserId(Integer userId);
    Optional<CartItem> findByCartUserIdAndProductId(Integer userId, Integer productId);
}
