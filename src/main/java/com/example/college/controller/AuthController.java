package com.example.college.controller;

import com.example.college.dto.AuthDtos.*;
import com.example.college.model.Role;
import com.example.college.model.User;
import com.example.college.security.JwtUtil;
import com.example.college.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public AuthController(UserService userService, AuthenticationManager authManager, JwtUtil jwtUtil, PasswordEncoder encoder){
        this.userService = userService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    @PostMapping("/student/register")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody RegisterStudentRequest req){
        User s = userService.createUser(req.fullName, req.email, req.password, Role.STUDENT);
        return ResponseEntity.ok(Map.of("id", s.getId(), "email", s.getEmail()));
    }

    @PostMapping("/teacher/register")
    public ResponseEntity<?> registerTeacher(@Valid @RequestBody RegisterTeacherRequest req){
        User t = userService.createUser(req.fullName, req.email, req.password, Role.TEACHER);
        return ResponseEntity.ok(Map.of("id", t.getId(), "email", t.getEmail()));
    }

    @PostMapping("/student/login")
    public ResponseEntity<?> loginStudent(@Valid @RequestBody LoginRequest req) {
        return loginWithRole(req, Role.STUDENT);
    }

    @PostMapping("/teacher/login")
    public ResponseEntity<?> loginTeacher(@Valid @RequestBody LoginRequest req) {
        return loginWithRole(req, Role.TEACHER);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(@Valid @RequestBody LoginRequest req) {
        return loginWithRole(req, Role.ADMIN);
    }

    private ResponseEntity<?> loginWithRole(LoginRequest req, Role expectedRole) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.email, req.password)
        );

        User user = userService.findByEmail(req.email).orElseThrow();

        if (!user.getRole().equals(expectedRole)) {
            return ResponseEntity.status(403).body(Map.of("message", "Access denied: Incorrect role"));
        }

        String token = jwtUtil.generateToken(
            user.getEmail(),
            Map.of("role", user.getRole().name(), "userId", user.getId())
        );

        return ResponseEntity.ok(new AuthResponse(token, user.getFullName(), user.getRole(), user.getId()));
    }
}
