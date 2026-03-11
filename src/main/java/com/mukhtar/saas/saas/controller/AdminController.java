package com.mukhtar.saas.saas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.servlet.http.HttpServletRequest;

import com.mukhtar.saas.saas.dto.CreateUserRequest;
import com.mukhtar.saas.saas.security.JwtService;
import com.mukhtar.saas.saas.service.AdminService;
import com.mukhtar.saas.saas.entity.User;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final JwtService jwtService;

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public User createUser(@RequestBody CreateUserRequest request,
                           HttpServletRequest httpRequest) {

        String authHeader = httpRequest.getHeader("Authorization");
        String token = authHeader.substring(7);

        Long adminUserId = jwtService.extractUserId(token);

        return adminService.createUser(
                request.getEmail(),
                request.getPassword(),
                request.getRole(),
                adminUserId
        );
    }
}