package com.example.college.repository;

import com.example.college.model.AptitudeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AptitudeQuestionRepository extends JpaRepository<AptitudeQuestion, Long> {

//    // Find all questions belonging to a section (Reasoning / Quantitative / Logical)
//    List<AptitudeQuestion> findBySection(String section);

	List<AptitudeQuestion> findBySectionContainingIgnoreCase(String section);

	//List<AptitudeQuestion> findBySectionIgnoreCase(String section);

}
