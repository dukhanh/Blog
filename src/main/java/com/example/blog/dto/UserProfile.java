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
public class UserProfile {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String phone;
    private List<Role> roles;
    private AddressDTO address;
}
