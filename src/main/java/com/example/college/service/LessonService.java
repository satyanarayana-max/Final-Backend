package com.example.college.service;

import com.example.college.model.Course;
import com.example.college.model.Lesson;
import com.example.college.repository.CourseRepository;
import com.example.college.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    // ----------------- ADD LESSON -----------------
    public Lesson addLessonToCourse(Long courseId, String title, String description, int orderIndex) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setDescription(description);
        lesson.setOrderIndex(orderIndex);
        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }

    // ----------------- UPDATE LESSON -----------------
    public Lesson update(Long id, String title, String description, Integer orderIndex) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        if (title != null) lesson.setTitle(title);
        if (description != null) lesson.setDescription(description);
        if (orderIndex != null) lesson.setOrderIndex(orderIndex);

        return lessonRepository.save(lesson);
    }

    // ----------------- DELETE LESSON -----------------
    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }

    // ----------------- GET SINGLE LESSON -----------------
    public Lesson get(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    // ----------------- LIST LESSONS BY COURSE -----------------
    public List<Lesson> listByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return lessonRepository.findByCourseOrderByOrderIndexAscIdAsc(course);
    }
}
