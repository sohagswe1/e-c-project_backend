package com.example.sj.controller;

import com.example.sj.entity.Address;
import com.example.sj.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    
    @Autowired
    private AddressService addressService;
    
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        return new ResponseEntity<>(addressService.save(address), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Address>> getAddressById(@PathVariable Integer id) {
        return ResponseEntity.ok(addressService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(addressService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        return ResponseEntity.ok(addressService.update(id, address));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
