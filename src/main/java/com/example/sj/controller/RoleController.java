package com.example.sj.controller;

import com.example.sj.entity.Role;
import com.example.sj.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return new ResponseEntity<>(roleService.save(role), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Role>> getRoleById(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Integer id, @RequestBody Role role) {
        return ResponseEntity.ok(roleService.update(id, role));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
