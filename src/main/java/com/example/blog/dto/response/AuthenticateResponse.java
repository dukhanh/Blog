package com.example.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class AuthenticateResponse {
    private String token;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
