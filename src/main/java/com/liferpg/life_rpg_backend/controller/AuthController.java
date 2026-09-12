package com.liferpg.life_rpg_backend.controller;

import com.liferpg.life_rpg_backend.dto.LoginRequest;
import com.liferpg.life_rpg_backend.dto.RegisterRequest;
import com.liferpg.life_rpg_backend.entity.User;
import com.liferpg.life_rpg_backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {

        User user = authService.register(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok("Registration successful for " + user.getName());
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        String token = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(token);
    }
}