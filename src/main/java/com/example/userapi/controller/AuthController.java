package com.example.userapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userapi.dto.AuthResponse;
import com.example.userapi.dto.LoginRequest;
import com.example.userapi.dto.RegisterRequest;
import com.example.userapi.model.AppUser;
import com.example.userapi.security.JwtUtil;
import com.example.userapi.service.AppUserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AppUserService appUserService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            String role = (req.getRole() == null || req.getRole().isBlank()) ? "ROLE_USER" : req.getRole();
            AppUser saved = appUserService.register(req.getUsername(), req.getPassword(), role);
            return ResponseEntity.ok(saved.getUsername());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        AppUser user = appUserService.findByUsername(req.getUsername());
        if (user == null) return ResponseEntity.status(401).body("invalid_credentials");

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            return ResponseEntity.status(401).body("invalid_credentials");

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
