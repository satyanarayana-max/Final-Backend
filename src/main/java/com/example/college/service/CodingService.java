package com.example.college.service;

import com.example.college.dto.CodingDTOs.*;
import com.example.college.model.*;
import com.example.college.repository.*;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CodingService {

    private final CodingQuestionRepository questionRepo;
    private final CodingTestCaseRepository testCaseRepo;
    private final CodingSubmissionRepository submissionRepo;
    private final LeaderboardService leaderboardService;

    public CodingService(
            CodingQuestionRepository questionRepo,
            CodingTestCaseRepository testCaseRepo,
            CodingSubmissionRepository submissionRepo,
            LeaderboardService leaderboardService
    ) {
        this.questionRepo = questionRepo;
        this.testCaseRepo = testCaseRepo;
        this.submissionRepo = submissionRepo;
        this.leaderboardService = leaderboardService;
    }

    // ✅ Add a new coding question
    public CodingQuestionResponse addQuestion(CodingQuestionRequest req) {
        CodingQuestion question = new CodingQuestion();
        question.setTitle(req.getTitle());
        question.setDescription(req.getDescription());
        question.setTopic(req.getTopic());
        question.setDifficulty(req.getDifficulty());
        question.setSampleInput(req.getSampleInput());
        question.setSampleOutput(req.getSampleOutput());

        CodingQuestion saved = questionRepo.save(question);

        for (TestCaseDTO tc : req.getTestCases()) {
            CodingTestCase entity = new CodingTestCase();
            entity.setQuestion(saved);
            entity.setInputData(tc.getInputData());
            entity.setExpectedOutput(tc.getExpectedOutput());
            entity.setHidden(tc.isHidden());
            testCaseRepo.save(entity);
        }

        return mapToResponse(saved);
    }

    // ✅ Get all coding questions
    public List<CodingQuestionResponse> getAllQuestions() {
        return questionRepo.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ Get all distinct topics
    public List<String> getAllTopics() {
        return questionRepo.findDistinctTopics();
    }

    // ✅ Get questions by topic
    public List<CodingQuestionResponse> getQuestionsByTopic(String topic) {
        return questionRepo.findByTopicIgnoreCase(topic).stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ Get question details with test cases
    public CodingQuestionResponse getQuestionDetails(Long id) {
        CodingQuestion question = questionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        CodingQuestionResponse response = mapToResponse(question);

        List<TestCaseDTO> testCases = testCaseRepo.findByQuestionId(id).stream()
                .map(tc -> new TestCaseDTO(tc.getInputData(), tc.getExpectedOutput(), tc.isHidden()))
                .toList();
       
        System.out.println("Test cases found: " + testCases.size());


        response.setTestCases(testCases);
        return response;
    }

    // ✅ Update a coding question
    public CodingQuestionResponse updateQuestion(Long id, CodingQuestionRequest req) {
        CodingQuestion question = questionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        question.setTitle(req.getTitle());
        question.setDescription(req.getDescription());
        question.setTopic(req.getTopic());
        question.setDifficulty(req.getDifficulty());
        question.setSampleInput(req.getSampleInput());
        question.setSampleOutput(req.getSampleOutput());

        CodingQuestion updated = questionRepo.save(question);

        testCaseRepo.deleteByQuestionId(id);
        for (TestCaseDTO tc : req.getTestCases()) {
            CodingTestCase entity = new CodingTestCase();
            entity.setQuestion(updated);
            entity.setInputData(tc.getInputData());
            entity.setExpectedOutput(tc.getExpectedOutput());
            entity.setHidden(tc.isHidden());
            testCaseRepo.save(entity);
        }

        return mapToResponse(updated);
    }

    // ✅ Delete a coding question
    public void deleteQuestion(Long id) {
        questionRepo.deleteById(id);
    }

    // ✅ Submit solution and evaluate test cases
    public CodingSubmissionResponse submitSolution(CodingSubmissionRequest req, User student) {
        // 1) load the question
        CodingQuestion question = questionRepo.findById(req.getQuestionId())
            .orElseThrow(() -> new RuntimeException("Question not found: " + req.getQuestionId()));

        // 2) build the submission from the frontend’s payload
        CodingSubmission submission = new CodingSubmission();
        submission.setQuestion(question);
        submission.setStudent(student);
        submission.setSubmittedCode(req.getCode());
        submission.setTotalTestCases(req.getTotalTestCases());
        submission.setPassedTestCases(req.getPassedTestCases());
        submission.setScore(req.getScore());
        submission.setCorrect(req.isCorrect());
        submission.setSubmittedAt(LocalDateTime.now());

        // 3) persist
        CodingSubmission saved = submissionRepo.save(submission);

        // 4) update leaderboard with exactly the score the frontend computed
        leaderboardService.updateCodingScore(student, req.getScore());

        // 5) echo back to client
        return new CodingSubmissionResponse(
            saved.getId(),
            question.getId(),
            req.getTotalTestCases(),
            req.getPassedTestCases(),
            req.getScore(),
            req.isCorrect()
        );
    }

    // ✅ Helper: map CodingQuestion to CodingQuestionResponse
    private CodingQuestionResponse mapToResponse(CodingQuestion q) {
        return new CodingQuestionResponse(
                q.getId(),
                q.getTitle(),
                q.getDescription(),
                q.getTopic(),
                q.getDifficulty(),
                q.getSampleInput(),
                q.getSampleOutput()
        );
    }
}
