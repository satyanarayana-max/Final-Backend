package com.example.college.repository;

import com.example.college.model.Quiz;
import com.example.college.model.QuizSubmission;
import com.example.college.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {
    Optional<QuizSubmission> findByQuizAndStudent(Quiz quiz, User student);
    List<QuizSubmission> findByStudent(User student);
}
