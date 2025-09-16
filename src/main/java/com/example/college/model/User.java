package com.example.college.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Instant createdAt = Instant.now();

    public User() {}
    public User(String fullName, String email, String password, Role role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role string) { this.role = string; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	
}
