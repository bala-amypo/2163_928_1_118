// package com.example.demo.service.impl;

// import com.example.demo.exception.BadRequestException;
// import com.example.demo.model.AppUser;
// import com.example.demo.repository.AppUserRepository;
// import com.example.demo.service.AuthService;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service
// public class AuthServiceImpl implements AuthService {

//     private final AppUserRepository repository;
//     private final PasswordEncoder encoder;

//     public AuthServiceImpl(AppUserRepository repository, PasswordEncoder encoder) {
//         this.repository = repository;
//         this.encoder = encoder;
//     }

//     @Override
//     public AppUser registerUser(AppUser user) {

//         if (repository.existsByEmail(user.getEmail())) {
//             throw new BadRequestException("Email already exists");
//         }

//         user.setPassword(encoder.encode(user.getPassword()));
//         return repository.save(user);
//     }

//     @Override
//     public AppUser findByEmail(String email) {
//         return repository.findByEmail(email)
//                 .orElseThrow(() -> new BadRequestException("Invalid credentials"));
//     }

//     @Override
//     public boolean existsByEmail(String email) {
//         return repository.existsByEmail(email);
//     }
// }


package com.example.demo.service.impl;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           AppUserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        // 1. Authenticate using Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Set Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generate Token
        String token = tokenProvider.generateToken(authentication);

        // 4. Retrieve User Details to return role/email
        AppUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("User not found after authentication"));

        return new JwtResponse(token, user.getEmail(), user.getRole().name());
    }

    @Override
    public AppUser register(RegisterRequest request) {
        // 1. Validate Email existence
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }
        // 2. Validate Username existence (if required by constraints)
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }

        // 3. Create new User entity
        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        // 4. Save and return
        return userRepository.save(user);
    }
}