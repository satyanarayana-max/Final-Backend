package com.example.college.service;

import com.example.college.model.Role;
import com.example.college.model.User;
import com.example.college.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final UserRepository userRepository;

    public StudentService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a new student
    public User create(String name, String email, String password) {
        User student = new User();
        student.setFullName(name);
        student.setEmail(email);
        student.setPassword(password);
        student.setRole(Role.STUDENT); // assuming roles are stored as String
        return userRepository.save(student);
    }

    // Update student details
    public User update(Long id, String name, String email) {
        User student = get(id);
        student.setFullName(name);
        student.setEmail(email);
        return userRepository.save(student);
    }

    // Delete student
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // Get student by ID
    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // List all students
    public List<User> list() {
        return userRepository.findByRole(Role.STUDENT); // Custom query in UserRepository
    }
}
