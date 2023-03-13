package com.example.blog.controller;

import com.example.blog.common.MessageContext;
import com.example.blog.dto.UserProfile;
import com.example.blog.dto.request.UpdateProfile;
import com.example.blog.dto.response.MessageResponse;
import com.example.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("profile")
    public ResponseEntity<UserProfile> getProfile(){
        UserProfile user = userService.getProfile();
        return ResponseEntity.ok(user);
    }

    @PutMapping("profile")
    public ResponseEntity<UserProfile> updateProfile(@RequestBody UpdateProfile updateProfile){
        UserProfile user = userService.updateProfile(updateProfile);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok(new MessageResponse(MessageContext.DELETE_USER_SUCCESS));
    }

    @PutMapping("{userId}")
    public ResponseEntity<?> assignAdmin(@PathVariable Long userId){
        userService.assignAdmin(userId);
        return ResponseEntity.ok(new MessageResponse(MessageContext.ASSIGN_ADMIN_SUCCESS));
    }
}
