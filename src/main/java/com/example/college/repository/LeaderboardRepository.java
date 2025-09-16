package com.example.college.repository;

import com.example.college.model.Leaderboard;
import com.example.college.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {

    // Find leaderboard entry by student ID
    Optional<Leaderboard> findByStudent_Id(Long studentId);

    // Find leaderboard entry by User entity
    Optional<Leaderboard> findByStudent(User student);

    // Get all leaderboard entries ordered by total score descending
    List<Leaderboard> findAllByOrderByTotalScoreDesc();

    // Optional: Get top N students by coding score
    List<Leaderboard> findTop10ByOrderByCodingScoreDesc();

    // Optional: Get top N students by aptitude score
    List<Leaderboard> findTop10ByOrderByAptitudeScoreDesc();

    // Optional: Get top N students by quiz score
    List<Leaderboard> findTop10ByOrderByQuizScoreDesc();
}
