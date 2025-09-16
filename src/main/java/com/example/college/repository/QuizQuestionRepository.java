package com.example.college.repository;

import com.example.college.model.Quiz;
import com.example.college.model.QuizQuestion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
	@Query("SELECT q FROM Quiz q LEFT JOIN FETCH q.questions WHERE q.id = :id")
    Optional<Quiz> findByIdWithQuestions(@Param("id") Long id);
}
