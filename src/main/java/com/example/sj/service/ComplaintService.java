package com.example.sj.service;

import com.example.sj.entity.Complaint;
import java.util.List;
import java.util.Optional;

public interface ComplaintService {
    Complaint save(Complaint complaint);
    Optional<Complaint> findById(Integer id);
    List<Complaint> findAll();
    Complaint update(Integer id, Complaint complaint);
    void delete(Integer id);
}
