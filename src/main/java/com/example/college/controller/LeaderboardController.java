package com.example.college.controller;

import com.example.college.dto.LeaderboardDTO;
import com.example.college.model.User;
import com.example.college.service.LeaderboardService;
import com.example.college.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;
    private final UserService userService; 

    public LeaderboardController(LeaderboardService leaderboardService,UserService userService) {
        this.leaderboardService = leaderboardService;
        this.userService=userService;
    }
    
    private User currentStudent(Authentication auth) {
        return userService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Student not found for email: " + auth.getName()));
    }

    @GetMapping
    public ResponseEntity<List<LeaderboardDTO>> getAllLeaderboard() {
        return ResponseEntity.ok(leaderboardService.getAllLeaderboard());
    }

    @GetMapping("/me")
    public ResponseEntity<LeaderboardDTO> getMyLeaderboard(Authentication auth) {
        User student = currentStudent(auth); // Extract current user from auth
        LeaderboardDTO dto = leaderboardService.getLeaderboardByStudentId(student.getId());
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

}
