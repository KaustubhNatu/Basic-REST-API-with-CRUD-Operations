package com.example.userapi.dto;

public class RegisterRequest {
    private String username;
    private String password;
    private String role; // optional

    // getters & setters
    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }
    public String getPassword() { return password; }
    public void setPassword(String p) { this.password = p; }
    public String getRole() { return role; }
    public void setRole(String r) { this.role = r; }
}
