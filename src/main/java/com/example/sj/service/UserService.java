package com.example.sj.service;

import com.example.sj.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findById(Integer id);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    User update(Integer id, User user);
    void delete(Integer id);
}
