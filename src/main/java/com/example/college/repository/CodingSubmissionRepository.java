package com.example.college.repository;

import com.example.college.model.CodingSubmission;
import com.example.college.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodingSubmissionRepository extends JpaRepository<CodingSubmission, Long> {

    // Fetch all submissions of a particular student
    List<CodingSubmission> findByStudent(User student);

    // Fetch all submissions for a specific coding question
    List<CodingSubmission> findByQuestionId(Long questionId);
}
