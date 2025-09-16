package com.example.college.controller;

import com.example.college.dto.AuthDtos.RegisterStudentRequest;
import com.example.college.dto.AuthDtos.RegisterTeacherRequest;
import com.example.college.model.*;
import com.example.college.service.CourseService;
import com.example.college.service.QuizService;
import com.example.college.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final CourseService courseService;
    private final QuizService quizService;

    public AdminController(UserService userService, CourseService courseService, QuizService quizService) {
        this.userService = userService;
        this.courseService = courseService;
        this.quizService = quizService;
    }

    // ================= USERS =================

    @PostMapping("/teachers")
    public ResponseEntity<?> createTeacher(@Valid @RequestBody RegisterTeacherRequest req){
        User t = userService.createUser(req.fullName, req.email, req.password, Role.TEACHER);
        return ResponseEntity.ok(Map.of("id", t.getId()));
    }

    @GetMapping("/teachers")
    public List<User> listTeachers(){ return userService.listByRole(Role.TEACHER); }

    @GetMapping("/students")
    public List<User> listStudents(){ return userService.listByRole(Role.STUDENT); }
    
    
    @PostMapping("/students")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody RegisterStudentRequest req){
        User s = userService.createUser(req.fullName, req.email, req.password, Role.STUDENT);
        return ResponseEntity.ok(Map.of("id", s.getId(), "email", s.getEmail()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ================= COURSES =================

//    @GetMapping("/courses")
//    public List<Course> listCourses(){ return courseService.list(); }
//
//    @PostMapping("/courses")
//    public ResponseEntity<Course> createCourse(@RequestBody Map<String,String> req){
//        String title = req.get("title");
//        String description = req.get("description");
//        Course c = courseService.create(title, description, null);
//        return ResponseEntity.ok(c);
//    }
//
//    @PutMapping("/courses/{id}")
//    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Map<String,String> req){
//        Course c = courseService.update(id, req.get("title"), req.get("description"));
//        return ResponseEntity.ok(c);
//    }
//
//    @DeleteMapping("/courses/{id}")
//    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
//        courseService.delete(id);
//        return ResponseEntity.noContent().build();
//    }

    // ================= QUIZZES =================

//    @GetMapping("/quizzes")
//    public List<Quiz> listQuizzes(){ return quizService.getAll(); }
//
//    @PostMapping("/quizzes")
//    public ResponseEntity<Quiz> createQuiz(@RequestBody Map<String,Object> req){
//        String title = (String) req.get("title");
//        Long courseId = Long.valueOf(req.get("courseId").toString());
//        Course course = courseService.get(courseId);
//        List<Map<String,Object>> qDtos = (List<Map<String,Object>>) req.get("questions");
//        Quiz quiz = quizService.createQuiz(title, course, null, qDtos);
//        return ResponseEntity.ok(quiz);
//    }
//
//    @PutMapping("/quizzes/{id}")
//    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Map<String,Object> req){
//        Quiz quiz = quizService.get(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
//        String title = (String) req.get("title");
//        Boolean published = req.get("published") != null ? (Boolean) req.get("published") : null;
//        List<Map<String,Object>> qDtos = (List<Map<String,Object>>) req.get("questions");
//        Quiz updated = quizService.updateQuiz(quiz, title, published, qDtos);
//        return ResponseEntity.ok(updated);
//    }
//
//    @DeleteMapping("/quizzes/{id}")
//    public ResponseEntity<?> deleteQuiz(@PathVariable Long id){
//        quizService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}
