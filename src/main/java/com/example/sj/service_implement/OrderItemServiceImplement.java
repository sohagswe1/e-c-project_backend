package com.example.sj.service_implement;

import com.example.sj.entity.OrderItem;
import com.example.sj.repository.OrderItemRepository;
import com.example.sj.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImplement implements OrderItemService {
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
    
    @Override
    public Optional<OrderItem> findById(Integer id) {
        return orderItemRepository.findById(id);
    }
    
    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }
    
    @Override
    public OrderItem update(Integer id, OrderItem orderItem) {
        return orderItemRepository.findById(id).map(existingOrderItem -> {
            OrderItem updatedOrderItem = OrderItem.builder()
                    .id(existingOrderItem.getId())
                    .order(orderItem.getOrder())
                    .product(orderItem.getProduct())
                    .quantity(orderItem.getQuantity())
                    .build();
            return orderItemRepository.save(updatedOrderItem);
        }).orElseThrow(() -> new RuntimeException("OrderItem not found"));
    }
    
    @Override
    public void delete(Integer id) {
        orderItemRepository.deleteById(id);
    }
}
