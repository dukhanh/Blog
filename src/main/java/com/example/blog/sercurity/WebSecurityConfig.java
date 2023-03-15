package com.example.blog.sercurity;

import com.example.blog.entity.role.RoleName;
import com.example.blog.exception.AccessDeniedExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationEntryPointJwt unauthenticatedHandler;

    private final AccessDeniedExceptionHandler accessDeniedHandler;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils,
                             AuthenticationEntryPointJwt unauthenticatedHandler,
                             AccessDeniedExceptionHandler accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.unauthenticatedHandler = unauthenticatedHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                .exceptionHandling().authenticationEntryPoint(unauthenticatedHandler).and()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/user/{userId}")
                .hasAuthority(RoleName.ROLE_ADMIN.toString())
                .requestMatchers(HttpMethod.PUT, "/api/user/{userId}")
                .hasAuthority(RoleName.ROLE_ADMIN.toString())
                .requestMatchers("/api/user/profile", "/api/album", "/api/post", "/api/comment")
                .hasAnyAuthority(RoleName.ROLE_ADMIN.toString(), RoleName.ROLE_USER.toString())
                .anyRequest()
                .authenticated();


        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(new AuthTokenFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}