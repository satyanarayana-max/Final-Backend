package com.example.college.dto;

import com.example.college.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuthDtos {
    public static class LoginRequest {
        @Email @NotBlank public String email;
        @NotBlank public String password;
    }
    public static class AuthResponse {
        public String token;
        public String fullName;
        public Role role;
        public Long userId;
        public AuthResponse(String token, String fullName, Role role, Long userId){
            this.token = token; this.fullName = fullName; this.role = role; this.userId = userId;
        }
    }
    public static class RegisterStudentRequest {
        @NotBlank public String fullName;
        @Email @NotBlank public String email;
        @NotBlank public String password;
    }
    public static class RegisterTeacherRequest {
        @NotBlank public String fullName;
        @Email @NotBlank public String email;
        @NotBlank public String password;
    }
}
