package com.example.sj.service;

import com.example.sj.entity.Role;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role save(Role role);
    Optional<Role> findById(Integer id);
    List<Role> findAll();
    Role update(Integer id, Role role);
    void delete(Integer id);
}
