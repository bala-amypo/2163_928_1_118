package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    public AuthController(CustomUserDetailsService userDetailsService,
                          JwtTokenProvider jwtTokenProvider,
                          PasswordEncoder passwordEncoder,
                          AppUserRepository appUserRepository) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/register")
    public AppUser register(@RequestBody RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        AppUser user = new AppUser(request.getEmail(), encodedPassword, request.getRole());
        return appUserRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        var userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        AppUser user = appUserRepository.findByEmail(request.getEmail()).get();
        return jwtTokenProvider.generateToken(user);
    }
}
