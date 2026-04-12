package com.example.sj.service;

import com.example.sj.entity.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment save(Payment payment);
    Optional<Payment> findById(Integer id);
    List<Payment> findAll();
    Payment update(Integer id, Payment payment);
    void delete(Integer id);
}
