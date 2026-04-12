package com.example.sj.service_implement;

import com.example.sj.entity.Customer;
import com.example.sj.repository.CustomerRepository;
import com.example.sj.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImplement implements CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
    
    @Override
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }
    
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    
    @Override
    public Customer update(Integer id, Customer customer) {
        return customerRepository.findById(id).map(existingCustomer -> {
            Customer updatedCustomer = Customer.builder()
                    .id(existingCustomer.getId())
                    .user(customer.getUser())
                    .loyaltyPoints(customer.getLoyaltyPoints())
                    .build();
            return customerRepository.save(updatedCustomer);
        }).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
    
    @Override
    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }
}
