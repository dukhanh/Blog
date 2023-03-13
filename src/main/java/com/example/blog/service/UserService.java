package com.example.blog.service;

import com.example.blog.dto.UserProfile;
import com.example.blog.dto.request.SignupRequest;
import com.example.blog.dto.request.UpdateProfile;
import com.example.blog.entity.User;

public interface UserService {

    User getCurrentUser();
    User findByUsername(String username);

    User findByEmail(String email);

    User findById(Long userId);

    void registerUser(SignupRequest signupRequest);

    UserProfile getProfile();
    UserProfile updateProfile(UpdateProfile updateProfile);

    void deleteUser(Long userId);

    void assignAdmin(Long userId);
}
