package com.example.college.controller;

import com.example.college.dto.CourseOutlineDto;
import com.example.college.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/courses")
public class CourseOutlineController {

    private final CourseService courseService;

    public CourseOutlineController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{courseId}/outline")
    public ResponseEntity<CourseOutlineDto> getCourseOutline(@PathVariable("courseId") Long courseId) {
        CourseOutlineDto outline = courseService.getCourseOutline(courseId);
        return ResponseEntity.ok(outline);
    }
}
