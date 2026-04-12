package com.example.sj.service_implement;

import com.example.sj.entity.User;
import com.example.sj.repository.UserRepository;
import com.example.sj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }
    
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public User update(Integer id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            User updatedUser = User.builder()
                    .id(existingUser.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .phone(user.getPhone())
                    .role(user.getRole())
                    .createdAt(user.getCreatedAt())
                    .build();
            return userRepository.save(updatedUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
