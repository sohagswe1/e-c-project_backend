package com.example.sj.service_implement;

import com.example.sj.entity.CartItem;
import com.example.sj.repository.CartItemRepository;
import com.example.sj.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImplement implements CartItemService {
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }
    
    @Override
    public Optional<CartItem> findById(Integer id) {
        return cartItemRepository.findById(id);
    }
    
    @Override
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }
    
    @Override
    public CartItem update(Integer id, CartItem cartItem) {
        return cartItemRepository.findById(id).map(existingCartItem -> {
            CartItem updatedCartItem = CartItem.builder()
                    .id(existingCartItem.getId())
                    .cart(cartItem.getCart())
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .build();
            return cartItemRepository.save(updatedCartItem);
        }).orElseThrow(() -> new RuntimeException("CartItem not found"));
    }
    
    @Override
    public void delete(Integer id) {
        cartItemRepository.deleteById(id);
    }
}
