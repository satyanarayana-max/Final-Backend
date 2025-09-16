package com.example.college.repository;

import com.example.college.model.Course;
import com.example.college.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacher(User teacher);
    List<Course> findByTeacherId(Long teacherId);
}
