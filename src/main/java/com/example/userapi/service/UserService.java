package com.example.userapi.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;

@Service
@Transactional
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) { this.repo = repo; }

    public User create(User user) {
        if (repo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return repo.save(user);
    }

    public User update(UUID id, User newUser) {
        return repo.findById(id).map(u -> {
            u.setName(newUser.getName());
            u.setEmail(newUser.getEmail());
            u.setAge(newUser.getAge());
            return repo.save(u);
        }).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional(readOnly = true)
    public User get(UUID id) { 
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional(readOnly = true)
    public List<User> getAll() { return repo.findAll(); }

    public void delete(UUID id) { repo.deleteById(id); }
}
