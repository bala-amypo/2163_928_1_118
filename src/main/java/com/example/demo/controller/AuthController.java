package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.AppUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    public JwtResponse register(@RequestBody RegisterRequest request) {
        return appUserService.register(request);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        return appUserService.login(request);
    }
}
