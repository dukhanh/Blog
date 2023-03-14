package com.example.blog.controller;

import com.example.blog.common.MessageContext;
import com.example.blog.dto.request.SignupRequest;
import com.example.blog.dto.response.AuthenticateResponse;
import com.example.blog.dto.request.LoginRequest;
import com.example.blog.dto.response.MessageResponse;
import com.example.blog.sercurity.JwtUtils;
import com.example.blog.sercurity.UserDetailsImpl;
import com.example.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticateResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String jwt = jwtUtils.generateJwtToken(userDetail.getUsername(), roles);
        return ResponseEntity.ok(new AuthenticateResponse(jwt,
                userDetail.getId(),
                userDetail.getUsername(),
                userDetail.getEmail(),
                roles
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userService.findByUsername(signupRequest.getUsername()) != null) {
            return ResponseEntity.badRequest().body(new MessageResponse(MessageContext.USERNAME_ALREADY));
        }
        if (userService.findByEmail(signupRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().body(new MessageResponse(MessageContext.EMAIL_ALREADY));
        }
        userService.registerUser(signupRequest);
        return ResponseEntity.ok(new MessageResponse(MessageContext.SIGNUP_USER_SUCCESS));
    }
}
