package com.example.college.service;

import com.example.college.model.Course;
import com.example.college.model.Enrollment;
import com.example.college.model.User;
import com.example.college.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentRepository repository;
    public EnrollmentService(EnrollmentRepository repository){ this.repository = repository; }

    public Enrollment enroll(User student, Course course){
        return repository.findByStudentAndCourse(student, course)
                .orElseGet(() -> repository.save(new Enrollment(student, course)));
    }
    public List<Enrollment> byStudent(User s){ return repository.findByStudent(s); }
    public List<Enrollment> byCourse(Course c){ return repository.findByCourse(c); }
}
