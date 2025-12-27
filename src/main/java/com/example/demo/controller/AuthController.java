// package com.example.demo.controller;

// import com.example.demo.dto.JwtResponse;
// import com.example.demo.dto.LoginRequest;
// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.model.AppUser;
// import com.example.demo.security.JwtTokenProvider;
// import com.example.demo.service.AuthService;
// import jakarta.validation.Valid;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/auth")
// public class AuthController {

//     private final AuthService authService;
//     private final JwtTokenProvider tokenProvider;

//     public AuthController(AuthService authService, JwtTokenProvider tokenProvider) {
//         this.authService = authService;
//         this.tokenProvider = tokenProvider;
//     }

//     @PostMapping("/register")
//     public String register(@Valid @RequestBody RegisterRequest req) {
//         AppUser user = new AppUser();
//         user.setEmail(req.getEmail());
//         user.setPassword(req.getPassword());
//         user.setRole(req.getRole());
//         authService.registerUser(user);
//         return "User registered successfully";
//     }

//     @PostMapping("/login")
//     public JwtResponse login(@Valid @RequestBody LoginRequest req) {
//         AppUser user = authService.findByEmail(req.getEmail());
//         String token = tokenProvider.generateToken(user);
//         return new JwtResponse(token);
//     }
// }

package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, AppUserRepository userRepository,
                          PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String token = tokenProvider.generateToken(authentication);
        AppUser user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return ResponseEntity.ok(new JwtResponse(token, user.getEmail(), user.getRole().name()));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AppUser>> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        AppUser saved = userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "User registered", saved));
    }
}
