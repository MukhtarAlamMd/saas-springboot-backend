package com.mukhtar.saas.saas.controller;

import com.mukhtar.saas.saas.dto.DashboardStats;
import com.mukhtar.saas.saas.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardStats getStats(Authentication authentication) {

        return dashboardService.getStats(authentication);
    }
}