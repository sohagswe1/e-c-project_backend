package com.example.sj.service_implement;

import com.example.sj.entity.Cart;
import com.example.sj.repository.CartRepository;
import com.example.sj.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImplement implements CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }
    
    @Override
    public Optional<Cart> findById(Integer id) {
        return cartRepository.findById(id);
    }
    
    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }
    
    @Override
    public Cart update(Integer id, Cart cart) {
        return cartRepository.findById(id).map(existingCart -> {
            Cart updatedCart = Cart.builder()
                    .id(existingCart.getId())
                    .user(cart.getUser())
                    .build();
            return cartRepository.save(updatedCart);
        }).orElseThrow(() -> new RuntimeException("Cart not found"));
    }
    
    @Override
    public void delete(Integer id) {
        cartRepository.deleteById(id);
    }
}
