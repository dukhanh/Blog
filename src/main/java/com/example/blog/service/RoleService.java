package com.example.blog.service;

import com.example.blog.entity.role.Role;
import com.example.blog.entity.role.RoleName;

public interface RoleService {
    Role findByName(RoleName name);
}
