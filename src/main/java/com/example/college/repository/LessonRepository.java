package com.example.college.repository;

import com.example.college.model.Lesson;
import com.example.college.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourseOrderByOrderIndexAscIdAsc(Course course);
}