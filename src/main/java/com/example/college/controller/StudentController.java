package com.example.college.controller;

import com.example.college.dto.QuizDtos.SubmitQuizRequest;
import com.example.college.model.*;
import com.example.college.service.CourseService;
import com.example.college.service.EnrollmentService;
import com.example.college.service.QuizService;
import com.example.college.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/student")

public class StudentController {

    private final UserService userService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final QuizService quizService;

    public StudentController(UserService userService, CourseService courseService, EnrollmentService enrollmentService, QuizService quizService){
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.quizService = quizService;
    }

    private User currentStudent(Authentication auth){
        return userService.findByEmail(auth.getName()).orElseThrow();
    }

//    // list all courses for browsing
//    @GetMapping("/courses")
//    public List<Map<String,Object>> listCourses(){
//        return courseService.list().stream().map(c -> {
//            Map<String,Object> m = new HashMap<>();
//            m.put("id", c.getId());
//            m.put("title", c.getTitle());
//            m.put("description", c.getDescription());
//
//            Map<String,Object> t = new HashMap<>();
//            if (c.getTeacherId() != null){
//                t.put("id", c.getTeacherId());
//                t.put("name", c.getTeacherName());  // or fullName
//            }
//
//            m.put("teacher", t);
//            return m;
//        }).toList();
//    }


    // enroll
    @PostMapping("{courseId}/enroll")
    public ResponseEntity<?> enroll(@PathVariable Long courseId, Authentication auth){
        Course c = courseService.get(courseId);
        Enrollment e = enrollmentService.enroll(currentStudent(auth), c);
        return ResponseEntity.ok(Map.of("enrollmentId", e.getId()));
    }

    // my courses
    @GetMapping("/my-courses")
    public List<Map<String,Object>> myCourses(Authentication auth){
        User student = currentStudent(auth);
        return enrollmentService.byStudent(student).stream().map(e -> {
            Course c = e.getCourse();
            Map<String,Object> m = new HashMap<>();
            m.put("id", c.getId());
            m.put("title", c.getTitle());
            m.put("description", c.getDescription());
            return m;
        }).toList();
    }

    // list quizzes for enrolled courses
    @GetMapping("/quiz")
    public List<Map<String,Object>> quizzes(Authentication auth){
        User student = currentStudent(auth);
        List<Map<String,Object>> list = new ArrayList<>();
        for (var e : enrollmentService.byStudent(student)){
            for (var q : quizService.byCourse(e.getCourse())){
                if (q.isPublished()){
                    Map<String,Object> m = new HashMap<>();
                    m.put("quizId", q.getId());
                    m.put("title", q.getTitle());
                    m.put("courseId", e.getCourse().getId());
                    list.add(m);
                }
            }
        }
        return list;
    }

    // submit quiz
    @PostMapping("/quizzes/submit")
    public Map<String,Object> submit(@Valid @RequestBody SubmitQuizRequest req, Authentication auth){
        User student = currentStudent(auth);
        Quiz quiz = quizService.get(req.quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        Map<Long,String> answers = new HashMap<>();
        if (req.answers != null) answers.putAll(req.answers);
        var submission = quizService.submit(quiz, student, answers);
        return Map.of("score", submission.getScore(), "submissionId", submission.getId());
    }

   // performance
//    @GetMapping("/performance")
//    public Map<String,Object> performance(Authentication auth){
//        User student = currentStudent(auth);
//        List<Map<String,Object>> courses = enrollmentService.byStudent(student).stream().map(e -> Map.of(
//                "courseId", e.getCourse().getId(),
//                "title", e.getCourse().getTitle()
//        )).toList();
//        // For simplicity, let frontend call a separate endpoint for detailed submissions if needed
//        return Map.of("courses", courses);
//    }
}
