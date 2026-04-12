package com.example.sj.service_implement;

import com.example.sj.entity.Role;
import com.example.sj.repository.RoleRepository;
import com.example.sj.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImplement implements RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
    
    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }
    
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
    
    @Override
    public Role update(Integer id, Role role) {
        return roleRepository.findById(id).map(existingRole -> {
            Role updatedRole = Role.builder()
                    .id(existingRole.getId())
                    .name(role.getName())
                    .build();
            return roleRepository.save(updatedRole);
        }).orElseThrow(() -> new RuntimeException("Role not found"));
    }
    
    @Override
    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }
}
