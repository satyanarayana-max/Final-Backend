package com.example.college.service;

import com.example.college.model.Role;
import com.example.college.model.User;
import com.example.college.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder){
        this.userRepository = userRepository; this.encoder = encoder;
    }

    public User createUser(String fullName, String email, String password, Role role){
        if(userRepository.existsByEmail(email)) throw new RuntimeException("Email already registered");
        User u = new User(fullName, email, encoder.encode(password), role);
        return userRepository.save(u);
    }

    public Optional<User> findByEmail(String email){ return userRepository.findByEmail(email); }
    public List<User> listByRole(Role role){ return userRepository.findByRole(role); }
    public List<User> listAll(){ return userRepository.findAll(); }
    public User getById(Long id){ return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")); }
    public void delete(Long id){ userRepository.deleteById(id); }
}
