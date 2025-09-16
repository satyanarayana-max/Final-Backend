package com.example.college.repository;

import com.example.college.model.Video;
import com.example.college.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByLessonOrderByOrderIndexAscIdAsc(Lesson lesson);
}