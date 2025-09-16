package com.example.college.repository;

import com.example.college.model.CodingTestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodingTestCaseRepository extends JpaRepository<CodingTestCase, Long> {

    // Fetch all test cases for a specific coding question
    List<CodingTestCase> findByQuestionId(Long questionId);

//    void deleteByQuestionId(Long questionId);
    @Modifying
    @Query("DELETE FROM CodingTestCase tc WHERE tc.question.id = :questionId")
    void deleteByQuestionId(@Param("questionId") Long questionId);


}
