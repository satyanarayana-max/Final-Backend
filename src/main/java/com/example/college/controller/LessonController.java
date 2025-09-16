package com.example.college.controller;

import com.example.college.model.Lesson;
import com.example.college.service.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class LessonController {
    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    // ----------------- CREATE LESSON -----------------
    @PostMapping("/courses/{courseId}/lessons")
    public Lesson addLesson(@PathVariable("courseId") Long courseId, @RequestBody Map<String, Object> body) {
        String title = (String) body.getOrDefault("title", "Untitled Lesson");
        String description = (String) body.getOrDefault("description", "");
        Number orderNum = (Number) body.getOrDefault("order", 0);
        int orderIndex = orderNum.intValue();

        return lessonService.addLessonToCourse(courseId, title, description, orderIndex);
    }

    // ----------------- LIST LESSONS -----------------
    @GetMapping("/courses/{courseId}/lessons")
    public List<Lesson> listLessons(@PathVariable Long courseId) {
        return lessonService.listByCourse(courseId);
    }

    // ----------------- UPDATE LESSON -----------------
    @PutMapping("/lessons/{id}")
    public Lesson updateLesson(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        String title = (String) body.get("title");
        String description = (String) body.get("description");
        Number orderNum = (Number) body.get("order");
        Integer orderIndex = orderNum == null ? null : orderNum.intValue();

        return lessonService.update(id, title, description, orderIndex);
    }

    // ----------------- DELETE LESSON -----------------
    @DeleteMapping("/lessons/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
        lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
