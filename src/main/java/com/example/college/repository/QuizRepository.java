package com.example.college.repository;

import com.example.college.model.Course;
import com.example.college.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCourse(Course course);
    
    @Query("SELECT q FROM Quiz q JOIN FETCH q.course")
    List<Quiz> findAllWithCourse();
    
//    @Query("SELECT q FROM Quiz q WHERE q.course.id = :courseId")
//    List<Quiz> findByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT q FROM Quiz q JOIN FETCH q.course WHERE q.course.id = :courseId")
    List<Quiz> findByCourseId(@Param("courseId") Long courseId);


}
