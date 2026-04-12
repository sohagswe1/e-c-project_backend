package com.example.sj.service_implement;

import com.example.sj.entity.Admin;
import com.example.sj.repository.AdminRepository;
import com.example.sj.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImplement implements AdminService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }
    
    @Override
    public Optional<Admin> findById(Integer id) {
        return adminRepository.findById(id);
    }
    
    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }
    
    @Override
    public Admin update(Integer id, Admin admin) {
        return adminRepository.findById(id).map(existingAdmin -> {
            Admin updatedAdmin = Admin.builder()
                    .id(existingAdmin.getId())
                    .user(admin.getUser())
                    .permissions(admin.getPermissions())
                    .build();
            return adminRepository.save(updatedAdmin);
        }).orElseThrow(() -> new RuntimeException("Admin not found"));
    }
    
    @Override
    public void delete(Integer id) {
        adminRepository.deleteById(id);
    }
}
