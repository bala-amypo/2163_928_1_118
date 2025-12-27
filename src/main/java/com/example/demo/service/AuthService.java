// package com.example.demo.service;

// import com.example.demo.model.AppUser;

// public interface AuthService {
//     AppUser registerUser(AppUser user);
//     AppUser findByEmail(String email);
//     boolean existsByEmail(String email);
// }


package com.example.demo.service;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.AppUser;

public interface AuthService {
    JwtResponse login(LoginRequest request);
    AppUser register(RegisterRequest request);
}