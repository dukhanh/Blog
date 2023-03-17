package com.example.blog.utils;

import com.example.blog.entity.User;
import com.example.blog.entity.role.Role;
import com.example.blog.entity.role.RoleName;
import com.example.blog.repository.RoleRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        Role roleUser = new Role(RoleName.ROLE_USER);
        Role roleAdmin = new Role(RoleName.ROLE_ADMIN);


        roleUser = roleRepository.save(roleUser);
        roleAdmin = roleRepository.save(roleAdmin);

        User user = new User();
        user.setEmail("dukhanhqt@gmail.com");
        user.setPassword(passwordEncoder.encode("adminblog"));
        user.setUsername("admin");
        user.setFirstName("Nguyen Van");
        user.setLastName("Blog");
        user.setRoles(Set.of(roleAdmin, roleUser));

        userRepository.save(user);
    }
}
