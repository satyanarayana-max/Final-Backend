package com.example.college.service;

import com.example.college.model.*;
import com.example.college.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class QuizService {

    private final QuizRepository quizRepo;
    private final QuizQuestionRepository questionRepo;
    private final QuizSubmissionRepository submissionRepo;
    private final LeaderboardService leaderboardService;
    private final ObjectMapper objectMapper;

    public QuizService(QuizRepository quizRepo,
                       QuizQuestionRepository questionRepo,
                       QuizSubmissionRepository submissionRepo,
                       LeaderboardService leaderboardService) {
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
        this.submissionRepo = submissionRepo;
        this.leaderboardService = leaderboardService;
        this.objectMapper = new ObjectMapper();
    }

    // ---------------- Create Quiz ----------------
    @Transactional
    public Quiz createQuiz(String title, Course course, User teacher, List<Map<String, Object>> qDtos, boolean published) {
        Quiz quiz = new Quiz(title, course, teacher);
        quiz.setPublished(published);
        quiz = quizRepo.save(quiz);

        if (qDtos != null) {
            for (Map<String, Object> q : qDtos) {
                QuizQuestion qq = new QuizQuestion(
                        quiz,
                        (String) q.get("question"),
                        (String) q.get("optionA"),
                        (String) q.get("optionB"),
                        (String) q.get("optionC"),
                        (String) q.get("optionD"),
                        (String) q.get("correctOption")
                );
                questionRepo.save(qq);
            }
        }

        return questionRepo.findByIdWithQuestions(quiz.getId()).orElseThrow();
    }

    // ---------------- Update Quiz ----------------
    @Transactional
    public Quiz updateQuiz(Quiz quiz, String title, Boolean published, List<Map<String, Object>> qDtos) {
        if (title != null) quiz.setTitle(title);
        if (published != null) quiz.setPublished(published);
        quiz = quizRepo.save(quiz);

        if (qDtos != null) {
            questionRepo.deleteAll(new ArrayList<>(quiz.getQuestions()));
            for (Map<String, Object> q : qDtos) {
                QuizQuestion qq = new QuizQuestion(
                        quiz,
                        (String) q.get("question"),
                        (String) q.get("optionA"),
                        (String) q.get("optionB"),
                        (String) q.get("optionC"),
                        (String) q.get("optionD"),
                        (String) q.get("correctOption")
                );
                questionRepo.save(qq);
            }
        }

        return questionRepo.findByIdWithQuestions(quiz.getId()).orElseThrow();
    }

    // ---------------- Fetch Quizzes ----------------
    public List<Quiz> byCourse(Course course) {
        return quizRepo.findByCourse(course);
    }

    public List<Quiz> byCourseId(Long courseId) {
        return quizRepo.findByCourseId(courseId);
    }

    @Transactional
    public Optional<Quiz> get(Long id) {
        return quizRepo.findById(id);
    }

    public List<Quiz> getAll() {
        return quizRepo.findAllWithCourse();
    }

    public void delete(Long id) {
        quizRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Quiz getWithQuestions(Long id) {
        return questionRepo.findByIdWithQuestions(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }
    
    

    // ---------------- Submission Logic ----------------
    @Transactional
    public QuizSubmission submit(Quiz quiz, User student, Map<Long, String> answers) {
        if (submissionRepo.findByQuizAndStudent(quiz, student).isPresent()) {
            throw new IllegalStateException("Quiz has already been submitted by this student.");
        }

        quiz.getQuestions().size(); // force load

        int total = quiz.getQuestions().size();
        int correct = 0;

        for (QuizQuestion qq : quiz.getQuestions()) {
            String ans = answers.get(qq.getId());
            String selectedOption = switch (ans) {
                case "optionA" -> "A";
                case "optionB" -> "B";
                case "optionC" -> "C";
                case "optionD" -> "D";
                default -> "";
            };

            if (!selectedOption.isEmpty() && selectedOption.equalsIgnoreCase(qq.getCorrectOption())) {
                correct++;
            }
        }

        int score = total == 0 ? 0 : (int) Math.round((correct * 100.0) / total);

        String json;
        try {
            json = objectMapper.writeValueAsString(answers);
        } catch (JsonProcessingException e) {
            json = "{}";
        }

        QuizSubmission submission = new QuizSubmission(quiz, student, json, score);
        submission.setAnswersJson(json);
        submission.setScore(score);

        leaderboardService.updateQuizScore(student, score);

        return submissionRepo.save(submission);
    }

    
    
    
    public Optional<QuizSubmission> findSubmission(Quiz quiz, User student) {
        return submissionRepo.findByQuizAndStudent(quiz, student);
    }
    
    
    

    public Map<Long, String> parseAnswers(String json) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory()
                    .constructMapType(Map.class, Long.class, String.class));
        } catch (JsonProcessingException e) {
            return new HashMap<>();
        }
    }

    public boolean existsById(Long id) {
        return quizRepo.existsById(id);
    }
}
