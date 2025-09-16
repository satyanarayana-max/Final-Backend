package com.example.college.repository;

import com.example.college.model.CodingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodingQuestionRepository extends JpaRepository<CodingQuestion, Long> {

    // ✅ Find all coding questions by topic
    List<CodingQuestion> findByTopic(String topic);

    // ✅ Case-insensitive topic filter
    List<CodingQuestion> findByTopicIgnoreCase(String topic);

    // ✅ Find all coding questions by difficulty
    List<CodingQuestion> findByDifficulty(String difficulty);

    // ✅ Filter by topic + difficulty
    List<CodingQuestion> findByTopicAndDifficulty(String topic, String difficulty);

    // ✅ Get distinct list of all topics
    @Query("SELECT DISTINCT q.topic FROM CodingQuestion q")
    List<String> findDistinctTopics();

    // ✅ Optional: search by title keyword
    List<CodingQuestion> findByTitleContainingIgnoreCase(String keyword);
}
