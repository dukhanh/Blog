package com.example.blog.service;

import com.example.blog.dto.request.SignupRequest;
import com.example.blog.entity.User;

public interface UserService {
    User findByUsername(String username);

    User findByEmail(String email);

    void registerUser(SignupRequest signupRequest);
}
