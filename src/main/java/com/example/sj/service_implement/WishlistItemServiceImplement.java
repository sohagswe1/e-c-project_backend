package com.example.sj.service_implement;

import com.example.sj.entity.WishlistItem;
import com.example.sj.repository.WishlistItemRepository;
import com.example.sj.service.WishlistItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistItemServiceImplement implements WishlistItemService {
    
    @Autowired
    private WishlistItemRepository wishlistItemRepository;
    
    @Override
    public WishlistItem save(WishlistItem wishlistItem) {
        return wishlistItemRepository.save(wishlistItem);
    }
    
    @Override
    public Optional<WishlistItem> findById(Integer id) {
        return wishlistItemRepository.findById(id);
    }
    
    @Override
    public List<WishlistItem> findAll() {
        return wishlistItemRepository.findAll();
    }
    
    @Override
    public WishlistItem update(Integer id, WishlistItem wishlistItem) {
        return wishlistItemRepository.findById(id).map(existingWishlistItem -> {
            WishlistItem updatedWishlistItem = WishlistItem.builder()
                    .id(existingWishlistItem.getId())
                    .wishlist(wishlistItem.getWishlist())
                    .product(wishlistItem.getProduct())
                    .build();
            return wishlistItemRepository.save(updatedWishlistItem);
        }).orElseThrow(() -> new RuntimeException("WishlistItem not found"));
    }
    
    @Override
    public void delete(Integer id) {
        wishlistItemRepository.deleteById(id);
    }
}
