package com.example.blog.service.impl;

import com.example.blog.entity.role.Role;
import com.example.blog.entity.role.RoleName;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.RoleRepository;
import com.example.blog.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(RoleName name) {
        return roleRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Role not found")
        );
    }
}
