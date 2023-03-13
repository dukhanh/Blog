package com.example.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class AuthenticateResponse {
    private String jwt;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
