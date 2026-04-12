package com.example.sj.controller;

import com.example.sj.entity.Customer;
import com.example.sj.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.update(id, customer));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
