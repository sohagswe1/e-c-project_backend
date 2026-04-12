package com.example.sj.repository;

import com.example.sj.entity.SellerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerOrderRepository extends JpaRepository<SellerOrder, Integer> {
}
