package com.example.sj.service_implement;

import com.example.sj.entity.Address;
import com.example.sj.repository.AddressRepository;
import com.example.sj.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImplement implements AddressService {
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }
    
    @Override
    public Optional<Address> findById(Integer id) {
        return addressRepository.findById(id);
    }
    
    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }
    
    @Override
    public Address update(Integer id, Address address) {
        return addressRepository.findById(id).map(existingAddress -> {
            Address updatedAddress = Address.builder()
                    .id(existingAddress.getId())
                    .user(address.getUser())
                    .addressLine(address.getAddressLine())
                    .city(address.getCity())
                    .postalCode(address.getPostalCode())
                    .country(address.getCountry())
                    .build();
            return addressRepository.save(updatedAddress);
        }).orElseThrow(() -> new RuntimeException("Address not found"));
    }
    
    @Override
    public void delete(Integer id) {
        addressRepository.deleteById(id);
    }
}
