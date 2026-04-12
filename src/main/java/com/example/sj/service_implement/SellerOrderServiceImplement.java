package com.example.sj.service_implement;

import com.example.sj.entity.SellerOrder;
import com.example.sj.repository.SellerOrderRepository;
import com.example.sj.service.SellerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SellerOrderServiceImplement implements SellerOrderService {
    
    @Autowired
    private SellerOrderRepository sellerOrderRepository;
    
    @Override
    public SellerOrder save(SellerOrder sellerOrder) {
        return sellerOrderRepository.save(sellerOrder);
    }
    
    @Override
    public Optional<SellerOrder> findById(Integer id) {
        return sellerOrderRepository.findById(id);
    }
    
    @Override
    public List<SellerOrder> findAll() {
        return sellerOrderRepository.findAll();
    }
    
    @Override
    public SellerOrder update(Integer id, SellerOrder sellerOrder) {
        return sellerOrderRepository.findById(id).map(existingSellerOrder -> {
            existingSellerOrder.setSeller(sellerOrder.getSeller());
            existingSellerOrder.setOrder(sellerOrder.getOrder());
            existingSellerOrder.setAmount(sellerOrder.getAmount());
            existingSellerOrder.setStatus(sellerOrder.getStatus());
            existingSellerOrder.setCreatedAt(sellerOrder.getCreatedAt());
            return sellerOrderRepository.save(existingSellerOrder);
        }).orElseThrow(() -> new RuntimeException("SellerOrder not found"));
    }
    
    @Override
    public void delete(Integer id) {
        sellerOrderRepository.deleteById(id);
    }
}
