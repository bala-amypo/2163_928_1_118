package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AppUserRepository appUserRepository,
                          PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Simple register (NO DTO)
    @PostMapping("/register")
    public AppUser register(@RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String role) {

        String encodedPassword = passwordEncoder.encode(password);
        AppUser user = new AppUser(email, encodedPassword, role);
        return appUserRepository.save(user);
    }

    // Simple login (NO JWT yet)
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {

        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return "Login successful";
    }
}
