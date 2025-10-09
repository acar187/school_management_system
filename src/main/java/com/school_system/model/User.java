package com.school_system.model;

public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String role; // "STUDENT", "TEACHER", "ADMIN"
    
    public User(int id, String username, String passwordHash, String role) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', role='" + role + "'}";
    }
}
