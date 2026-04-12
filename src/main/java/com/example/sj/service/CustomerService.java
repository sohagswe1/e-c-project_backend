package com.example.sj.service;

import com.example.sj.entity.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer save(Customer customer);
    Optional<Customer> findById(Integer id);
    List<Customer> findAll();
    Customer update(Integer id, Customer customer);
    void delete(Integer id);
}
