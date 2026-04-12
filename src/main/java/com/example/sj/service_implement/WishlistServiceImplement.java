package com.example.sj.service_implement;

import com.example.sj.entity.Wishlist;
import com.example.sj.repository.WishlistRepository;
import com.example.sj.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImplement implements WishlistService {
    
    @Autowired
    private WishlistRepository wishlistRepository;
    
    @Override
    public Wishlist save(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }
    
    @Override
    public Optional<Wishlist> findById(Integer id) {
        return wishlistRepository.findById(id);
    }
    
    @Override
    public List<Wishlist> findAll() {
        return wishlistRepository.findAll();
    }
    
    @Override
    public Wishlist update(Integer id, Wishlist wishlist) {
        return wishlistRepository.findById(id).map(existingWishlist -> {
            Wishlist updatedWishlist = Wishlist.builder()
                    .id(existingWishlist.getId())
                    .user(wishlist.getUser())
                    .build();
            return wishlistRepository.save(updatedWishlist);
        }).orElseThrow(() -> new RuntimeException("Wishlist not found"));
    }
    
    @Override
    public void delete(Integer id) {
        wishlistRepository.deleteById(id);
    }
}
