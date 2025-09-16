package com.example.college.controller;

import com.example.college.dto.AuthDtos.RegisterStudentRequest;
import com.example.college.dto.CourseDtos.*;
import com.example.college.dto.QuizDtos.*;
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
@RequestMapping("/api/teacher")

public class TeacherController {

    private final UserService userService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final QuizService quizService;

    public TeacherController(UserService userService, CourseService courseService, EnrollmentService enrollmentService, QuizService quizService){
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.quizService = quizService;
    }

    private User currentTeacher(Authentication auth){
        return userService.findByEmail(auth.getName()).orElseThrow();
    }
    
    @GetMapping("/students")
    public List<User> listStudents(){ return userService.listByRole(Role.STUDENT); }
    
    
    @PostMapping("/students")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody RegisterStudentRequest req){
        User s = userService.createUser(req.fullName, req.email, req.password, Role.STUDENT);
        return ResponseEntity.ok(Map.of("id", s.getId(), "email", s.getEmail()));
    }
    

    // Courses CRUD
//    @PostMapping("/courses")
//    public Course createCourse(@Valid @RequestBody CreateCourseRequest req, Authentication auth){
//        return courseService.create(req.title, req.description, currentTeacher(auth));
//    }
//
//    @PutMapping("/courses/{id}")
//    public Course updateCourse(@PathVariable Long id, @Valid @RequestBody UpdateCourseRequest req){
//        return courseService.update(id, req.title, req.description);
//    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/courses")
//    public List<Course> myCourses(Authentication auth){
//        return courseService.listByTeacher(currentTeacher(auth));
//    }

    // Quizzes CRUD
//    @PostMapping("/quizzes")
//    public Quiz createQuiz(@Valid @RequestBody CreateQuizRequest req, Authentication auth){
//        Course course = courseService.get(req.courseId);
//        List<Map<String,Object>> qDtos = new ArrayList<>();
//        if (req.questions != null){
//            for (Question q : req.questions){
//                Map<String,Object> m = new HashMap<>();
//                m.put("question", q.question);
//                m.put("optionA", q.optionA);
//                m.put("optionB", q.optionB);
//                m.put("optionC", q.optionC);
//                m.put("optionD", q.optionD);
//                m.put("correctOption", q.correctOption);
//                qDtos.add(m);
//            }
//        }
//        return quizService.createQuiz(req.title, course, currentTeacher(auth), qDtos);
//    }

    @PutMapping("/quizzes/{id}")
    public Quiz updateQuiz(@PathVariable Long id, @Valid @RequestBody UpdateQuizRequest req){
        Quiz quiz = quizService.get(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
        List<Map<String,Object>> qDtos = null;
        if(req.questions != null){
            qDtos = new ArrayList<>();
            for (Question q : req.questions){
                Map<String,Object> m = new HashMap<>();
                m.put("question", q.question);
                m.put("optionA", q.optionA);
                m.put("optionB", q.optionB);
                m.put("optionC", q.optionC);
                m.put("optionD", q.optionD);
                m.put("correctOption", q.correctOption);
                qDtos.add(m);
            }
        }
        return quizService.updateQuiz(quiz, req.title, req.published, qDtos);
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id){
        quizService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/courses/{courseId}/quizzes")
    public List<Quiz> listQuizzes(@PathVariable Long courseId){
        Course c = courseService.get(courseId);
        return quizService.byCourse(c);
    }

    // Monitor student performance in teacher's courses
    @GetMapping("/{studentId}")
    public Map<String,Object> monitor(@PathVariable Long studentId){
        Map<String,Object> result = new HashMap<>();
        // For simplicity: list student enrollments and quiz submissions
        result.put("enrollments", List.of()); // Frontend can call student endpoints too
        result.put("submissions", List.of());
        return result;
    }
}
