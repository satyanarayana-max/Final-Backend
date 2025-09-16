package com.example.college.controller;

import com.example.college.dto.AptitudeDTOs.*;
import com.example.college.model.User;
import com.example.college.service.AptitudeService;
import com.example.college.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/aptitude")
public class AptitudeController {

    private final AptitudeService aptitudeService;
    private final UserService userService;
    

    public AptitudeController(AptitudeService aptitudeService, UserService userService) {
        this.aptitudeService = aptitudeService;
        this.userService = userService;
    }

    /**
     * Extracts the currently authenticated student from the security context.
     */
    private User currentStudent(Authentication auth) {
        return userService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Student not found for email: " + auth.getName()));
    }

    /**
     * Creates a new aptitude question.
     */
    @PostMapping("/questions")
    public ResponseEntity<AptitudeQuestionResponse> createQuestion(@RequestBody AptitudeQuestionRequest request) {
        AptitudeQuestionResponse response = aptitudeService.createQuestion(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Submits an answer for an aptitude question and returns evaluation result.
     */
    @PostMapping("/submit")
    public ResponseEntity<AptitudeSubmissionResponse> submitAnswer(
            @RequestBody AptitudeSubmissionRequest request,
            Authentication auth
    ) {
        User student = currentStudent(auth);
        AptitudeSubmissionResponse response = aptitudeService.submitAnswer(student.getId(), request);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves aptitude questions filtered by section.
     */
    @GetMapping("/questions")
    public ResponseEntity<List<AptitudeQuestionResponse>> getQuestionsBySection(@RequestParam("section") String section) {
        List<AptitudeQuestionResponse> questions = aptitudeService.getQuestionsBySection(section);
        return ResponseEntity.ok(questions);
    }

    /**
     * Deletes an aptitude question by ID.
     */
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Long id) {
        aptitudeService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/submission-status")
    public ResponseEntity<?> getSubmissionStatus(@RequestParam("questionId") Long questionId, Authentication auth) {
        User student = currentStudent(auth);
        return ResponseEntity.ok(aptitudeService.getSubmissionStatus(student, questionId));
    }


}
