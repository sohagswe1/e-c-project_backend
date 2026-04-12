package com.example.sj.service_implement;

import com.example.sj.entity.Order;
import com.example.sj.repository.OrderRepository;
import com.example.sj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplement implements OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
    
    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }
    
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    
    @Override
    public Order update(Integer id, Order order) {
        return orderRepository.findById(id).map(existingOrder -> {
            Order updatedOrder = Order.builder()
                    .id(existingOrder.getId())
                    .user(order.getUser())
                    .totalPrice(order.getTotalPrice())
                    .status(order.getStatus())
                    .createdAt(order.getCreatedAt())
                    .build();
            return orderRepository.save(updatedOrder);
        }).orElseThrow(() -> new RuntimeException("Order not found"));
    }
    
    @Override
    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }
}
