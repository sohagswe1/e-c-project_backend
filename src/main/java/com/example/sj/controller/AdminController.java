package com.example.sj.controller;

import com.example.sj.entity.Admin;
import com.example.sj.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        return new ResponseEntity<>(adminService.save(admin), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Admin>> getAdminById(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Integer id, @RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.update(id, admin));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Integer id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
