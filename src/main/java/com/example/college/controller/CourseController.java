package com.example.college.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.college.dto.CourseResponseDTO;
import com.example.college.model.Course;
import com.example.college.model.User;
import com.example.college.repository.UserRepository;
import com.example.college.service.CourseService;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final UserRepository userRepository;

    public CourseController(CourseService courseService, UserRepository userRepository) {
        this.courseService = courseService;
        this.userRepository = userRepository;
    }

    // ================== GET ALL COURSES ==================
    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> listAllCourses() {
        try {
            List<CourseResponseDTO> courses = courseService.listAllCourses(); // fetch from service
            if (courses == null || courses.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }

            List<CourseResponseDTO> courseDTOs = courses.stream()
                    .map(c -> new CourseResponseDTO(
                            c.getId(),
                            c.getTitle(),
                            c.getDescription(),
                            c.getCategory(),
                            c.getThumbnailUrl(),
                            c.getBannerUrl()
                    ))
                    .toList();

            return ResponseEntity.ok(courseDTOs); // 200 OK with data
        } catch (Exception e) {
            // Log the exception if you have a logger
            e.printStackTrace();
            return ResponseEntity.status(500).build(); // 500 Internal Server Error
        }
    }

    // ================== GET COURSE DETAILS BY ID ==================
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseDetails(@PathVariable("courseId") Long courseId) {
        Course course = courseService.get(courseId);
        return ResponseEntity.ok(course);
    }

    // ================== GET COURSES BY TEACHER ==================
//    @GetMapping("/teacher") // ✅ new endpoint
//    public ResponseEntity<List<Course>> getCoursesByTeacher(Authentication authentication) {
//        String username = authentication.getName();
//        User teacher = userRepository.findByEmail(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        List<Course> courses = courseService.listByTeacher(teacher.getId());
//        return ResponseEntity.ok(courses);
//    }
    @GetMapping("/teacher")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByTeacher(Authentication authentication) {
        String username = authentication.getName();
        User teacher = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<CourseResponseDTO> courses = courseService.listByTeacherDTO(teacher.getId());
        return ResponseEntity.ok(courses);
    }



    // ================== CREATE COURSE ==================
//    @PostMapping(consumes = {"multipart/form-data"})
//    public ResponseEntity<Course> createCourse(
//            @RequestPart("title") String title,
//            @RequestPart("description") String description,
//            @RequestPart("category") String category,
//            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
//            @RequestPart(value = "banner", required = false) MultipartFile banner,
//            Authentication authentication
//    ) throws IOException {
//        String username = authentication.getName();
//        User teacher = userRepository.findByEmail(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        Course course = courseService.createWithFiles(title, description, category, thumbnail, banner, teacher);
//        return ResponseEntity.ok(course);
//    }

    
    
    
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<CourseResponseDTO> createCourse(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestParam(value = "banner", required = false) MultipartFile banner,
            Authentication authentication
    ) throws IOException {
        String username = authentication.getName();
        User teacher = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseService.createWithFiles(title, description, category, thumbnail, banner, teacher);

        CourseResponseDTO dto = new CourseResponseDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCategory(),
                course.getThumbnailUrl(),
                course.getBannerUrl()
        );
        return ResponseEntity.ok(dto);
    }
    // ================== UPDATE COURSE ==================
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart("category") String category,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestPart(value = "banner", required = false) MultipartFile banner
    ) throws IOException {
        Course c = courseService.updateWithFiles(id, title, description, category, thumbnail, banner);
        return ResponseEntity.ok(c);
    }

    // ================== DELETE COURSE ==================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
