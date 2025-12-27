package com.example.demo.service;

import com.example.demo.model.AppUser;

public interface AuthService {
    AppUser registerUser(AppUser user);
    AppUser findByEmail(String email);
    boolean existsByEmail(String email);
}
