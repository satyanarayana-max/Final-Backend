package com.example.college.service;

import com.example.college.dto.LeaderboardDTO;
import com.example.college.model.Leaderboard;
import com.example.college.model.User;
import com.example.college.repository.LeaderboardRepository;
import com.example.college.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;
    private final UserRepository userRepository;

    public LeaderboardService(LeaderboardRepository leaderboardRepository, UserRepository userRepository) {
        this.leaderboardRepository = leaderboardRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get all leaderboard entries ordered by total score
     */
    public List<LeaderboardDTO> getAllLeaderboard() {
        return leaderboardRepository.findAllByOrderByTotalScoreDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get leaderboard entry by student ID
     */
    public LeaderboardDTO getLeaderboardByStudentId(Long studentId) {
        return leaderboardRepository.findByStudent_Id(studentId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public void updateCodingScore(User student, int score) {
        Leaderboard lb = leaderboardRepository.findByStudent(student)
                .orElse(new Leaderboard(student, 0, 0, 0));
        lb.setCodingScore(lb.getCodingScore() + score); // ✅ accumulate
        leaderboardRepository.save(lb);
    }

    public void updateAptitudeScore(User student, int score) {
        Leaderboard lb = leaderboardRepository.findByStudent(student)
                .orElse(new Leaderboard(student, 0, 0, 0));
        lb.setAptitudeScore(lb.getAptitudeScore() + score); // ✅ accumulate
        leaderboardRepository.save(lb);
    }

    public void updateQuizScore(User student, int score) {
        Leaderboard lb = leaderboardRepository.findByStudent(student)
                .orElse(new Leaderboard(student, 0, 0, 0));
        lb.setQuizScore(lb.getQuizScore() + score); // ✅ accumulate
        leaderboardRepository.save(lb);
    }


    /**
     * Create or update leaderboard entry with all scores
     */
    public LeaderboardDTO saveOrUpdateLeaderboard(Long studentId, int codingScore, int aptitudeScore, int quizScore) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        Leaderboard lb = leaderboardRepository.findByStudent(student)
                .orElse(new Leaderboard(student, 0, 0, 0));

        lb.setCodingScore(codingScore);
        lb.setAptitudeScore(aptitudeScore);
        lb.setQuizScore(quizScore);

        Leaderboard saved = leaderboardRepository.save(lb);
        return convertToDTO(saved);
    }

    /**
     * Convert Leaderboard entity to DTO
     */
    private LeaderboardDTO convertToDTO(Leaderboard lb) {
        LeaderboardDTO dto = new LeaderboardDTO();
        dto.setStudentId(lb.getStudent().getId());
        dto.setStudentName(lb.getStudent().getFullName());
        dto.setCodingScore(lb.getCodingScore());
        dto.setAptitudeScore(lb.getAptitudeScore());
        dto.setQuizScore(lb.getQuizScore());
        dto.setTotalScore(lb.getTotalScore());
        return dto;
    }
}
