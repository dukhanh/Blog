package com.example.blog.service.impl;

import com.example.blog.dto.request.SignupRequest;
import com.example.blog.entity.User;
import com.example.blog.entity.role.Role;
import com.example.blog.entity.role.RoleName;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.RoleService;
import com.example.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void registerUser(SignupRequest signupRequest) {
        Role roleUser= this.roleService.findByName(RoleName.ROLE_USER);
        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);

        User user = this.modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        this.userRepository.save(user);
    }
}
