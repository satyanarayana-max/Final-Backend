package com.example.college.controller;

import com.example.college.dto.CodingDTOs.*;
import com.example.college.model.User;
import com.example.college.service.CodingService;
import com.example.college.repository.UserRepository;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coding")
public class CodingController {

    private static final Logger logger = LoggerFactory.getLogger(CodingController.class);

    private final CodingService codingService;
    private final UserRepository userRepository;

    public CodingController(CodingService codingService, UserRepository userRepository) {
        this.codingService = codingService;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedStudent(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }

    // ✅ Add a new coding question
    @PostMapping("/questions")
    public ResponseEntity<CodingQuestionResponse> addQuestion(@Valid @RequestBody CodingQuestionRequest request) {
        logger.info("📥 Adding coding question: {}", request.getTitle());

        if (request.getTestCases() == null || request.getTestCases().isEmpty()) {
            logger.warn("❌ Missing test cases for question: {}", request.getTitle());
            return ResponseEntity.badRequest().build();
        }

        CodingQuestionResponse response = codingService.addQuestion(request);
        logger.info("✅ Question added with ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    // ✅ Get all coding questions
    @GetMapping("/questions")
    public ResponseEntity<List<CodingQuestionResponse>> getAllQuestions() {
        logger.info("📤 Retrieving all coding questions");
        List<CodingQuestionResponse> questions = codingService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    // ✅ Get distinct coding topics
    @GetMapping("/topics")
    public ResponseEntity<List<String>> getAllTopics() {
        logger.info("📤 Retrieving all coding topics");
        return ResponseEntity.ok(codingService.getAllTopics());
    }

    // ✅ Get questions by topic
    @GetMapping("/questions/by-topic")
    public ResponseEntity<List<CodingQuestionResponse>> getQuestionsByTopic(@RequestParam("topic") String topic) {
        logger.info("📤 Retrieving questions for topic: {}", topic);
        return ResponseEntity.ok(codingService.getQuestionsByTopic(topic));
    }

    // ✅ Get question details with test cases
    @GetMapping("/questions/{id}")
    public ResponseEntity<CodingQuestionResponse> getQuestionDetails(@PathVariable("id") Long id) {
        logger.info("📤 Retrieving details for question ID: {}", id);
        return ResponseEntity.ok(codingService.getQuestionDetails(id));
    }

    // ✅ Delete a coding question
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Long id) {
        logger.info("🗑️ Deleting coding question ID: {}", id);
        codingService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Update a coding question
    @PutMapping("/questions/{id}")
    public ResponseEntity<CodingQuestionResponse> updateQuestion(
            @PathVariable("id") Long id,
            @Valid @RequestBody CodingQuestionRequest request
    ) {
        logger.info("✏️ Updating coding question ID: {}", id);
        return ResponseEntity.ok(codingService.updateQuestion(id, request));
    }

    // ✅ Submit solution for evaluation
    @PostMapping("/submit")
    public ResponseEntity<CodingSubmissionResponse> submitSolution(
            @Valid @RequestBody CodingSubmissionRequest request,
            Authentication auth
    ) {
        User student = getAuthenticatedStudent(auth);
        logger.info("📥 Submission received from student: {} for question ID: {}", student.getId(), request.getQuestionId());
        CodingSubmissionResponse response = codingService.submitSolution(request, student);
        return ResponseEntity.ok(response);
    }
}
