package com.example.sj.service;

import com.example.sj.entity.Admin;
import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin save(Admin admin);
    Optional<Admin> findById(Integer id);
    List<Admin> findAll();
    Admin update(Integer id, Admin admin);
    void delete(Integer id);
}
