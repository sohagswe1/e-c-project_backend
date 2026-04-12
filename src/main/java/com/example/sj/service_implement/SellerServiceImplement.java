package com.example.sj.service_implement;

import com.example.sj.entity.Seller;
import com.example.sj.repository.SellerRepository;
import com.example.sj.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImplement implements SellerService {
    
    @Autowired
    private SellerRepository sellerRepository;
    
    @Override
    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }
    
    @Override
    public Optional<Seller> findById(Integer id) {
        return sellerRepository.findById(id);
    }
    
    @Override
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }
    
    @Override
    public Seller update(Integer id, Seller seller) {
        return sellerRepository.findById(id).map(existingSeller -> {
            Seller updatedSeller = Seller.builder()
                    .id(existingSeller.getId())
                    .user(seller.getUser())
                    .shopName(seller.getShopName())
                    .tradeLicense(seller.getTradeLicense())
                    .bankAccount(seller.getBankAccount())
                    .status(seller.getStatus())
                    .createdAt(seller.getCreatedAt())
                    .build();
            return sellerRepository.save(updatedSeller);
        }).orElseThrow(() -> new RuntimeException("Seller not found"));
    }
    
    @Override
    public void delete(Integer id) {
        sellerRepository.deleteById(id);
    }
}
