package com.example.sj.controller;

import com.example.sj.entity.Role;
import com.example.sj.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        try {
            // ⚠️ ADMIN role create করার জন্য special check
            if (role.getName() != null && role.getName().equalsIgnoreCase("ADMIN")) {
                // ADMIN role শুধুমাত্র ADMIN user create করতে পারবে
                // এটি PermissionEvaluator দিয়ে check করা হয়
                throw new org.springframework.security.access.AccessDeniedException(
                        "Only ADMIN users can create ADMIN roles"
                );
            }
            
            // যে কোনো অন্যান্য role (CUSTOMER, SELLER etc) সবাই create করতে পারবে
            return new ResponseEntity<>(roleService.save(role), HttpStatus.CREATED);
        } catch (org.springframework.security.access.AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error creating role: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Integer id) {
        try {
            Optional<Role> role = roleService.findById(id);
            if (role.isPresent()) {
                return ResponseEntity.ok(role);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Role not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        try {
            List<Role> roles = roleService.findAll();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error fetching roles: " + e.getMessage());
        }
    }
    
    @PreAuthorize("@permissionEvaluator.isAdmin()")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @RequestBody Role role) {
        try {
            // Check if role exists
            Optional<Role> existingRole = roleService.findById(id);
            if (!existingRole.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Role not found");
            }
            
            return ResponseEntity.ok(roleService.update(id, role));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error updating role: " + e.getMessage());
        }
    }
    
    @PreAuthorize("@permissionEvaluator.isAdmin()")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer id) {
        try {
            // Check if role exists
            Optional<Role> existingRole = roleService.findById(id);
            if (!existingRole.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Role not found");
            }
            
            roleService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error deleting role: " + e.getMessage());
        }
    }
}
