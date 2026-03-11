package com.mukhtar.saas.saas.controller;

import com.mukhtar.saas.saas.dto.LoginRequest;
import com.mukhtar.saas.saas.dto.RegisterCompanyRequest;
import com.mukhtar.saas.saas.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register-company")
    public String register(@RequestBody RegisterCompanyRequest request) {
        authService.registerCompany(request);
        return "Company Registered Successfully!";
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.logins(request);
    }
}