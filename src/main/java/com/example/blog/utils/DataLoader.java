package com.example.blog.utils;

import com.example.blog.entity.role.Role;
import com.example.blog.entity.role.RoleName;
import com.example.blog.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
//        roleRepository.save(new Role(RoleName.ROLE_USER));
//        roleRepository.save(new Role(RoleName.ROLE_ADMIN));
    }
}
