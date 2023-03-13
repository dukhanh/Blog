package com.example.blog.service.impl;

import com.example.blog.common.MessageContext;
import com.example.blog.dto.UserProfile;
import com.example.blog.dto.request.SignupRequest;
import com.example.blog.dto.request.UpdateProfile;
import com.example.blog.entity.Address;
import com.example.blog.entity.User;
import com.example.blog.entity.role.Role;
import com.example.blog.entity.role.RoleName;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.RoleService;
import com.example.blog.service.UserService;
import com.example.blog.utils.MappingUpdate;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    private final MappingUpdate mappingUpdate;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder, RoleService roleService,
                           MappingUpdate mappingUpdate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.mappingUpdate = mappingUpdate;
    }

    @Override
    public User getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.findByUsername(username);
        if (user == null) throw new ResourceNotFoundException(MessageContext.USER_NOT_FOUND);
        return user;
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
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void registerUser(SignupRequest signupRequest) {
        Role roleUser = this.roleService.findByName(RoleName.ROLE_USER);
        Set<Role> roles = new LinkedHashSet<>();
        roles.add(roleUser);

        User user = this.modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        this.userRepository.save(user);
    }

    @Override
    public UserProfile getProfile() {
        User currentUser = this.getCurrentUser();
        return this.modelMapper.map(currentUser, UserProfile.class);
    }

    @Override
    public UserProfile updateProfile(UpdateProfile updateProfile) {
        User currentUser = this.getCurrentUser();
        Address address = currentUser.getAddress();
        if (address == null) {
            address = new Address();
        }
        this.mappingUpdate.copyNonNullProperties(updateProfile.getAddress(), address);
        this.mappingUpdate.copyNonNullProperties(updateProfile, currentUser);
        currentUser.setAddress(address);
        currentUser = this.userRepository.save(currentUser);
        return this.modelMapper.map(currentUser, UserProfile.class);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = this.findById(userId);
        userRepository.delete(user);
    }

    @Override
    public void assignAdmin(Long userId) {
        User user = this.findById(userId);
        Role roleAdmin = this.roleService.findByName(RoleName.ROLE_ADMIN);
        user.getRoles().add(roleAdmin);
        userRepository.save(user);
    }
}
