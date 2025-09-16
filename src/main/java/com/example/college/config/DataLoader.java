package com.example.college.config;

import com.example.college.model.Role;
import com.example.college.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(UserService users){
        return args -> {
            try{
                users.createUser("Super Admin", "admin@college.com", "admin123", Role.ADMIN);
            } catch (RuntimeException ignored) {}
        };
    }
}
