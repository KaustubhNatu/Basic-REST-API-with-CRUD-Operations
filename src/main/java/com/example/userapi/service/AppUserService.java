package com.example.userapi.service;

import com.example.userapi.model.AppUser;
import com.example.userapi.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    public AppUser register(String username, String rawPassword, String role) {
        if (repo.existsByUsername(username)) {
            throw new RuntimeException("Username already taken");
        }

        String encrypted = encoder.encode(rawPassword);

        AppUser user = new AppUser(username, encrypted, role);
        return repo.save(user);
    }

    public AppUser findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }
}
