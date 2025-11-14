package com.example.userapi.service;

import com.example.userapi.dto.UserRequest;
import com.example.userapi.exception.ResourceNotFoundException;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User create(UserRequest req) {
        String emailNormalized = req.getEmail().trim().toLowerCase();
        if (repo.existsByEmail(emailNormalized)) {
            throw new IllegalArgumentException("email already exists");
        }

        User u = new User(UUID.randomUUID(),
                          req.getName().trim(),
                          emailNormalized,
                          req.getAge());
        return repo.save(u);
    }

    public User getById(UUID id) {
        return repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found with id: " + id));
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User update(UUID id, UserRequest req) {
        User existing = repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found with id: " + id));

        existing.setName(req.getName().trim());
        existing.setEmail(req.getEmail().trim().toLowerCase());
        existing.setAge(req.getAge());
        return repo.save(existing);
    }

    public void delete(UUID id) {
        if (!repo.findById(id).isPresent()) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        repo.deleteById(id);
    }
}
