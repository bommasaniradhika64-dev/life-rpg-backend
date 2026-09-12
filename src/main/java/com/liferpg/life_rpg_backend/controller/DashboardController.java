package com.liferpg.life_rpg_backend.controller;

import com.liferpg.life_rpg_backend.dto.DashboardResponse;
import com.liferpg.life_rpg_backend.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rpg")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(
            Authentication authentication) {

        String email = authentication.getName();

        DashboardResponse dashboard =
                dashboardService.getDashboard(email);

        return ResponseEntity.ok(dashboard);
    }
}