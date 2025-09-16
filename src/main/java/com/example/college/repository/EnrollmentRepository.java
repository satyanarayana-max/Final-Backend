package com.example.college.repository;

import com.example.college.model.Course;
import com.example.college.model.Enrollment;
import com.example.college.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(User student);
    List<Enrollment> findByCourse(Course course);
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);
}
