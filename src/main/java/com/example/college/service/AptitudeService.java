package com.example.college.service;

import com.example.college.dto.AptitudeDTOs.*;
import com.example.college.model.AptitudeQuestion;
import com.example.college.model.AptitudeSubmission;
import com.example.college.model.User;
import com.example.college.repository.AptitudeQuestionRepository;
import com.example.college.repository.AptitudeSubmissionRepository;
import com.example.college.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AptitudeService {

    private final AptitudeQuestionRepository questionRepository;
    private final AptitudeSubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final LeaderboardService leaderboardService;

   

    public AptitudeService(
            AptitudeQuestionRepository questionRepository,
            AptitudeSubmissionRepository submissionRepository,
            UserRepository userRepository,
            LeaderboardService leaderboardService
    ) {
        this.questionRepository = questionRepository;
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
        this.leaderboardService = leaderboardService;
    }

    // ✅ Create a new aptitude question
    public AptitudeQuestionResponse createQuestion(AptitudeQuestionRequest request) {
        AptitudeQuestion question = new AptitudeQuestion();
        question.setSection(request.getSection());
        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectOption(request.getCorrectOption());
       // question.setCorrectAnswer(request.getCorrectAnswer()); // optional if needed
        question.setMarks(request.getMarks());

        AptitudeQuestion saved = questionRepository.save(question);
        return mapToResponse(saved);
    }

    // ✅ Fetch questions by section
    public List<AptitudeQuestionResponse> getQuestionsBySection(String section) {
        List<AptitudeQuestion> entities = questionRepository.findBySectionContainingIgnoreCase(section);
        return entities.stream().map(this::mapToResponse).toList();
    }
    
    //Delete question 
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }


    // ✅ Submit an answer to a question
    public AptitudeSubmissionResponse submitAnswer(Long studentId, AptitudeSubmissionRequest request) {
        // Fetch question and student
        AptitudeQuestion question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("❌ Question not found with ID: " + request.getQuestionId()));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("❌ Student not found with ID: " + studentId));

        boolean isCorrect = question.getCorrectOption().equalsIgnoreCase(request.getChosenOption());

        // Check if student already submitted a correct answer for this question
        boolean alreadyCorrect = submissionRepository.hasCorrectSubmission(question.getId(), student.getId());


        int score = 0;
        if (isCorrect && !alreadyCorrect) {
            score = question.getMarks(); // ✅ award marks only on first correct attempt
            leaderboardService.updateAptitudeScore(student, score);
        }

        // Save this attempt regardless of correctness
        AptitudeSubmission submission = new AptitudeSubmission();
        submission.setQuestion(question);
        submission.setStudent(student);
        submission.setChosenOption(request.getChosenOption());
        submission.setCorrect(isCorrect);
        submission.setScore(score); // ✅ 0 if repeated or incorrect
        submission.setSubmittedAt(LocalDateTime.now());

        AptitudeSubmission saved = submissionRepository.save(submission);

        return new AptitudeSubmissionResponse(
                saved.getId(),
                question.getId(),
                isCorrect,
                score
        );
    }

    
    
    public Map<String, Object> getSubmissionStatus(User student, Long questionId) {
        boolean alreadySubmitted = submissionRepository.existsByQuestion_IdAndStudent_Id(questionId, student.getId());

        AptitudeSubmission submission = submissionRepository
            .findTopByQuestion_IdAndStudent_IdOrderBySubmittedAtDesc(questionId, student.getId())
            .orElse(null);

        boolean correct = submission != null && submission.isCorrect();
        int score = submission != null ? submission.getScore() : 0;

        return Map.of(
            "alreadySubmitted", alreadySubmitted,
            "correct", correct,
            "score", score
        );
    }



    // ✅ Helper method to map entity to response DTO
    private AptitudeQuestionResponse mapToResponse(AptitudeQuestion question) {
        return new AptitudeQuestionResponse(
                question.getId(),
                question.getSection(),
                question.getQuestionText(),
                question.getOptionA(),
                question.getOptionB(),
                question.getOptionC(),
                question.getOptionD(),
                question.getCorrectOption(),
                question.getMarks()
        );
    }
}
