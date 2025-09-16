package com.example.college.repository;

import com.example.college.model.AptitudeQuestion;
import com.example.college.model.AptitudeSubmission;
import com.example.college.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AptitudeSubmissionRepository extends JpaRepository<AptitudeSubmission, Long> {

    // Find all submissions made by a specific student
    List<AptitudeSubmission> findByStudent(User student);

    // Find all submissions for a specific question
    List<AptitudeSubmission> findByQuestionId(Long questionId);
    
    Optional<AptitudeSubmission> findByQuestionAndStudent(AptitudeQuestion question, User student);
    
    boolean existsByQuestion_IdAndStudent_Id(Long questionId, Long studentId);


    Optional<AptitudeSubmission> findTopByQuestion_IdAndStudent_IdOrderBySubmittedAtDesc(Long questionId, Long studentId);
    
    
    @Query("SELECT COUNT(s) > 0 FROM AptitudeSubmission s WHERE s.question.id = :questionId AND s.student.id = :studentId AND s.isCorrect = true")
    boolean hasCorrectSubmission(@Param("questionId") Long questionId, @Param("studentId") Long studentId);






}
