package com.todolist.todolist.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.todolist.todolist.security.filter.AuthenticationFilter;
import com.todolist.todolist.security.filter.ExceptionHandlerFilter;
import com.todolist.todolist.security.filter.JWTAuthorizationFilter;
import com.todolist.todolist.security.manager.CustomAuthenticationManager;
import com.todolist.todolist.user.UserService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    
    private CustomAuthenticationManager customAuthenticationManager;
    private UserService userService;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager, userService);
        authenticationFilter.setFilterProcessesUrl("/authenticate");

        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(SecurityConstants.REGISTER_PATH).permitAll()
            .requestMatchers(SecurityConstants.CONFIRM_PATH).permitAll()
            .requestMatchers(SecurityConstants.RESETREQUEST_PATH).permitAll()
            .requestMatchers(SecurityConstants.RESETRESPONSE_PATH).permitAll()
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            .requestMatchers(SecurityConstants.REFRESH_TOKEN_PATH).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
        .addFilter(authenticationFilter)
        .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
