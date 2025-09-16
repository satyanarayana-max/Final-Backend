package com.example.college.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.college.dto.EnrollmentDto;
import com.example.college.model.Course;
import com.example.college.model.Enrollment;
import com.example.college.model.User;
import com.example.college.service.CourseService;
import com.example.college.service.EnrollmentService;
import com.example.college.service.QuizService;
import com.example.college.service.UserService;


@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class EnrollmentController {
	
	 private final UserService userService;
	    private final CourseService courseService;
	    private final EnrollmentService enrollmentService;
	   ;

	    public EnrollmentController(UserService userService, CourseService courseService, EnrollmentService enrollmentService, QuizService quizService){
	        this.userService = userService;
	        this.courseService = courseService;
	        this.enrollmentService = enrollmentService;
	       
	    }

	    private User currentStudent(Authentication auth){
	        return userService.findByEmail(auth.getName()).orElseThrow();
	    }

	    
	    
	    @PostMapping("{courseId}/enroll")
	    public ResponseEntity<?> enroll(@PathVariable("courseId") Long courseId, Authentication auth) {
	    	System.out.println("enroll calling");
	    	Course c = courseService.get(courseId);
	    	 System.out.println("course "+c);
		     System.out.println("student"+currentStudent(auth));
	        Enrollment e = enrollmentService.enroll(currentStudent(auth), c);
	        System.out.println("enroll leaving");
	        return ResponseEntity.ok(Map.of("enrollmentId", e.getId()));
	    }
	    
	    
	    @GetMapping("{courseId}/isEnrolled")
	    public ResponseEntity<?> isEnrolled(@PathVariable("courseId") Long courseId, Authentication auth) {
	        User student = currentStudent(auth);
	        Course course = courseService.get(courseId);
	        boolean enrolled = enrollmentService.byStudent(student)
	                            .stream()
	                            .anyMatch(e -> e.getCourse().getId().equals(courseId));
	        return ResponseEntity.ok(Map.of("enrolled", enrolled));
	    }


	    
	    

}
