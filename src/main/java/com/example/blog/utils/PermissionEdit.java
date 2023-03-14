package com.example.blog.utils;

import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.entity.role.RoleName;
import com.example.blog.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PermissionEdit {
    private final UserService userService;

    public PermissionEdit(UserService userService) {
        this.userService = userService;
    }

    public boolean checkPermissionUpdateDelete(Long userid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername((String) authentication.getPrincipal());
        return userid.equals(user.getId()) || authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()));
    }
}
