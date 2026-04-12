package com.example.sj.controller;

import com.example.sj.entity.User;
import com.example.sj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
