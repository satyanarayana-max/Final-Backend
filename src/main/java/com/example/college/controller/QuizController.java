package com.example.college.controller;

import com.example.college.dto.QuizAttemptDto;
import com.example.college.dto.QuizDto;
import com.example.college.dto.QuizDtos;
import com.example.college.dto.QuizDtos.*;
import com.example.college.model.Course;
import com.example.college.model.Quiz;
import com.example.college.model.QuizSubmission;
import com.example.college.model.User;
import com.example.college.service.CourseService;
import com.example.college.service.QuizService;
import com.example.college.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final UserService userService;
    private final CourseService courseService;
    private final QuizService quizService;
    

    public QuizController(UserService userService, CourseService courseService, QuizService quizService) {
        this.userService = userService;
        this.courseService = courseService;
        this.quizService = quizService;
    }

    private User currentUser(Authentication auth) {
        return userService.findByEmail(auth.getName()).orElseThrow();
    }

 // ---------------- QuizController ----------------

    @PostMapping
    public ResponseEntity<?> createQuiz(@Valid @RequestBody QuizDtos.CreateQuizRequest req, Authentication auth) {
        try {
            Course course = courseService.get(req.courseId);
            User teacher = currentUser(auth);

            // Prepare questions list
            List<Map<String, Object>> qDtos = new ArrayList<>();
            if (req.questions != null) {
                for (QuizDtos.Question q : req.questions) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("question", q.question);
                    m.put("optionA", q.optionA);
                    m.put("optionB", q.optionB);
                    m.put("optionC", q.optionC);
                    m.put("optionD", q.optionD);
                    m.put("correctOption", q.correctOption);
                    qDtos.add(m);
                }
            }

            // For creation, default published to false
            boolean published = false;

            Quiz quiz = quizService.createQuiz(req.title, course, teacher, qDtos, published);
            return ResponseEntity.ok(new QuizDto(quiz));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Quiz creation failed", "details", e.getMessage()));
        }
    }





    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuiz(@PathVariable Long id, @Valid @RequestBody QuizDtos.UpdateQuizRequest req) {
        try {
            Quiz quiz = quizService.get(id).orElseThrow(() -> new RuntimeException("Quiz not found"));

            // Prepare questions if provided
            List<Map<String, Object>> qDtos = null;
            if (req.questions != null) {
                qDtos = new ArrayList<>();
                for (QuizDtos.Question q : req.questions) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("question", q.question);
                    m.put("optionA", q.optionA);
                    m.put("optionB", q.optionB);
                    m.put("optionC", q.optionC);
                    m.put("optionD", q.optionD);
                    m.put("correctOption", q.correctOption);
                    qDtos.add(m);
                }
            }

            // Use published value from request, default to current value
            boolean published = req.published != null ? req.published : quiz.isPublished();

            Quiz updated = quizService.updateQuiz(quiz, req.title, published, qDtos);

            // Force initialize course
            updated.getCourse().getTitle();

            return ResponseEntity.ok(new QuizDto(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Quiz update failed", "details", e.getMessage()));
        }
    }


    // Delete quiz
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        if (!quizService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        quizService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // List all quizzes
    @GetMapping
    public List<QuizDto> getAllQuizzes() {
        return quizService.getAll().stream()
                .map(QuizDto::new)
                .collect(Collectors.toList());
    }

//    // List quizzes by course
//    @GetMapping("/courses/{courseId}")
//    public List<QuizDto> listQuizzes(@PathVariable("courseId") Long courseId) {
//        Course course = courseService.get(courseId);
//        return quizService.byCourse(course).stream()
//                .map(QuizDto::new)
//                .collect(Collectors.toList());
//    }
    
    @GetMapping("/courses/{courseId}")
    public List<QuizDto> listQuizzes(@PathVariable("courseId") Long courseId) {
        return quizService.byCourseId(courseId).stream()
                .map(QuizDto::new)
                .collect(Collectors.toList());
    }

    
    
    @GetMapping("/{id}/attempt")
    public ResponseEntity<?> takeQuiz(@PathVariable("id") Long id) {
        Quiz quiz = quizService.getWithQuestions(id);

        if (!quiz.isPublished()) {
            return ResponseEntity.status(403).body(Map.of("error", "Quiz not published yet"));
        }

        QuizAttemptDto dto = new QuizAttemptDto();
        dto.quizId = quiz.getId();
        dto.title = quiz.getTitle();
        dto.questions = quiz.getQuestions().stream().map(q -> {
            QuizAttemptDto.QuestionDto qDto = new QuizAttemptDto.QuestionDto();
            qDto.id = q.getId();
            qDto.question = q.getQuestion();
            qDto.optionA = q.getOptionA();
            qDto.optionB = q.getOptionB();
            qDto.optionC = q.getOptionC();
            qDto.optionD = q.getOptionD();
            return qDto;
        }).toList();

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/submit")
    @Transactional
    public ResponseEntity<?> submitQuiz(@Valid @RequestBody SubmitQuizRequest req, Authentication auth) {
        try {
            User student = currentUser(auth);
            Quiz quiz = quizService.get(req.quizId)
                    .orElseThrow(() -> new RuntimeException("Quiz not found"));

            // Prevent duplicate submission
            Optional<QuizSubmission> existing = quizService.findSubmission(quiz, student);
            if (existing.isPresent()) {
                return ResponseEntity.status(409).body(Map.of("error", "Quiz already submitted"));
            }

            // Save new submission and calculate score
            QuizSubmission submission = quizService.submit(quiz, student, req.answers);
            Map<Long, String> answers = req.answers;

            // Build detailed results
            List<Map<String, Object>> detailedResults = quiz.getQuestions().stream().map(q -> {
                Map<String, Object> m = new HashMap<>();
                String selected = answers.getOrDefault(q.getId(), "Not Answered");

                String yourAnswerText = switch (selected) {
                    case "optionA" -> q.getOptionA();
                    case "optionB" -> q.getOptionB();
                    case "optionC" -> q.getOptionC();
                    case "optionD" -> q.getOptionD();
                    default -> "Not Answered";
                };

                String correctAnswerText = switch (q.getCorrectOption()) {
                    case "A" -> q.getOptionA();
                    case "B" -> q.getOptionB();
                    case "C" -> q.getOptionC();
                    case "D" -> q.getOptionD();
                    default -> "";
                };

                m.put("question", q.getQuestion());
                m.put("yourAnswer", yourAnswerText);
                m.put("correctAnswer", correctAnswerText);
                m.put("isCorrect", yourAnswerText.equals(correctAnswerText));
                return m;
            }).toList();

            // Build response
            Map<String, Object> resp = new HashMap<>();
            resp.put("quizId", quiz.getId());
            resp.put("title", quiz.getTitle());
            resp.put("score", submission.getScore());
            resp.put("submittedAt", submission.getSubmittedAt());
            resp.put("totalQuestions", quiz.getQuestions().size());
            resp.put("correctAnswers", (int) detailedResults.stream().filter(m -> (Boolean) m.get("isCorrect")).count());
            resp.put("results", detailedResults);
            resp.put("answers", answers);

            return ResponseEntity.ok(resp);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Quiz submission failed", "details", e.getMessage()));
        }
    }

    
    
    
    @GetMapping("/{id}/submission")
    public ResponseEntity<?> getSubmission(@PathVariable("id") Long id, Authentication auth) {
        try {
            User student = currentUser(auth);
            Quiz quiz = quizService.getWithQuestions(id);
            Optional<QuizSubmission> submissionOpt = quizService.findSubmission(quiz, student);
            
            System.out.println("calling getsubmission method");
            System.out.println(submissionOpt);

            if (submissionOpt.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("error", "No submission found"));
            }

            QuizSubmission submission = submissionOpt.get();
            Map<Long, String> answers = quizService.parseAnswers(submission.getAnswersJson());

            return ResponseEntity.ok(buildResultResponse(quiz, answers, submission.getScore()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to fetch submission", "details", e.getMessage()));
        }
    }

    
    private Map<String, Object> buildResultResponse(Quiz quiz, Map<Long, String> answers, int score) {
        List<Map<String, Object>> detailedResults = quiz.getQuestions().stream().map(q -> {
            Map<String, Object> m = new HashMap<>();
            String selected = answers.getOrDefault(q.getId(), "Not Answered");

            String yourAnswerText = switch (selected) {
                case "optionA" -> q.getOptionA();
                case "optionB" -> q.getOptionB();
                case "optionC" -> q.getOptionC();
                case "optionD" -> q.getOptionD();
                default -> "Not Answered";
            };

            String correctAnswerText = switch (q.getCorrectOption()) {
                case "A" -> q.getOptionA();
                case "B" -> q.getOptionB();
                case "C" -> q.getOptionC();
                case "D" -> q.getOptionD();
                default -> "";
            };

            m.put("question", q.getQuestion());
            m.put("yourAnswer", yourAnswerText);
            m.put("correctAnswer", correctAnswerText);
            m.put("isCorrect", yourAnswerText.equals(correctAnswerText));
            return m;
        }).collect(Collectors.toList());

        Map<String, Object> resp = new HashMap<>();
        resp.put("quizId", quiz.getId());
        resp.put("title", quiz.getTitle());
        resp.put("score", score);
        
        resp.put("totalQuestions", quiz.getQuestions().size());
        resp.put("correctAnswers", (int) detailedResults.stream().filter(m -> (Boolean) m.get("isCorrect")).count());
        resp.put("results", detailedResults);
        resp.put("answers", answers);

        return resp;
    }

    

}
