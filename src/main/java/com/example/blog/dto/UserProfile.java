package com.example.blog.dto;

import com.example.blog.entity.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile extends UserDTO{
    private List<Role> roles;
    private AddressDTO address;
}
