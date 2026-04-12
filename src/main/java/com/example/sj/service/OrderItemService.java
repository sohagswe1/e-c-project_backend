package com.example.sj.service;

import com.example.sj.entity.OrderItem;
import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);
    Optional<OrderItem> findById(Integer id);
    List<OrderItem> findAll();
    OrderItem update(Integer id, OrderItem orderItem);
    void delete(Integer id);
}
