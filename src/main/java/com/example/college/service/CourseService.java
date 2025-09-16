package com.example.college.service;

import com.example.college.dto.CourseOutlineDto;
import com.example.college.dto.CourseResponseDTO;
import com.example.college.dto.LessonDto;
import com.example.college.dto.VideoDto;
import com.example.college.model.Course;
import com.example.college.model.User;
import com.example.college.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // ---------------- LIST COURSES BY TEACHER ----------------
    public List<Course> listByTeacher(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    public List<CourseResponseDTO> listByTeacherDTO(Long teacherId) {
        List<Course> courses = courseRepository.findByTeacherId(teacherId);
        return courses.stream()
                .map(c -> new CourseResponseDTO(
                        c.getId(),
                        c.getTitle(),
                        c.getDescription(),
                        c.getCategory(),
                        c.getThumbnailUrl(),
                        c.getBannerUrl()
                ))
                .toList();
    }

    // ---------------- GET COURSE OUTLINE ----------------
    @Transactional(readOnly = true)
    public CourseOutlineDto getCourseOutline(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<LessonDto> lessons = course.getLessons().stream()
                .map(lesson -> {
                    List<VideoDto> videos = lesson.getVideos().stream()
                            .map(video -> new VideoDto(
                                    video.getId(),
                                    video.getTitle(),
                                    video.getUrl(),
                                    video.getDurationSeconds(),
                                    video.getOrderIndex(),
                                    video.getPreview()
                            )).toList();

                    return new LessonDto(
                            lesson.getId(),
                            lesson.getTitle(),
                            lesson.getDescription(),
                            lesson.getOrderIndex(),
                            videos
                    );
                }).toList();

        return new CourseOutlineDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getThumbnailUrl(),
                course.getBannerUrl(),
                lessons
        );
    }


    // ---------------- CREATE COURSE WITH FILES ----------------
    public Course createWithFiles(String title, String description, String category,
                                  MultipartFile thumbnail, MultipartFile banner, User teacher) throws IOException {
        String thumbnailPath = (thumbnail != null && !thumbnail.isEmpty()) ? saveFile(thumbnail) : null;
        String bannerPath = (banner != null && !banner.isEmpty()) ? saveFile(banner) : null;

        Course course = new Course(title, description, category, thumbnailPath, bannerPath, teacher);
        return courseRepository.save(course);
    }

    // ---------------- UPDATE COURSE WITH FILES ----------------
    public Course updateWithFiles(Long id, String title, String description, String category,
                                  MultipartFile thumbnail, MultipartFile banner) throws IOException {
        Course course = get(id);
        course.setTitle(title);
        course.setDescription(description);
        course.setCategory(category);

        if (thumbnail != null && !thumbnail.isEmpty()) {
            course.setThumbnailUrl(saveFile(thumbnail));
        }
        if (banner != null && !banner.isEmpty()) {
            course.setBannerUrl(saveFile(banner));
        }

        return courseRepository.save(course);
    }

    // ---------------- HELPER METHOD TO SAVE FILE ----------------
    private String saveFile(MultipartFile file) throws IOException {
        // Absolute path where files will be stored
        String folder = System.getProperty("user.dir") + "/uploads/";

        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdirs(); // create folder if it doesn't exist
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(dir, filename);
        file.transferTo(dest);

        // Return relative path for frontend (you can serve this via WebConfig)
        return "http://localhost:8080/uploads/" + filename;
    }

    // ---------------- DELETE COURSE ----------------
    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found with id " + id);
        }
        courseRepository.deleteById(id); // cascades lessons + videos
    }

    // ---------------- GET COURSE ----------------
    public Course get(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    // ---------------- LIST ALL COURSES ----------------
    public List<CourseResponseDTO> listAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(c -> new CourseResponseDTO(
                        c.getId(),
                        c.getTitle(),
                        c.getDescription(),
                        c.getCategory(),
                        c.getThumbnailUrl(),
                        c.getBannerUrl()
                ))
                .toList();
    }
}
