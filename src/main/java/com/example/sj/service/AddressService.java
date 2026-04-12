package com.example.sj.service;

import com.example.sj.entity.Address;
import java.util.List;
import java.util.Optional;

public interface AddressService {
    Address save(Address address);
    Optional<Address> findById(Integer id);
    List<Address> findAll();
    Address update(Integer id, Address address);
    void delete(Integer id);
}
