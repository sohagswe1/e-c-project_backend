package com.example.sj.service;

import com.example.sj.entity.Order;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);
    Optional<Order> findById(Integer id);
    List<Order> findAll();
    Order update(Integer id, Order order);
    void delete(Integer id);
}
