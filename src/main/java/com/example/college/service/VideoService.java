package com.example.college.service;

import com.example.college.dto.VideoDto;
import com.example.college.model.Lesson;
import com.example.college.model.Video;
import com.example.college.repository.LessonRepository;
import com.example.college.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final LessonRepository lessonRepository;

    @Value("${app.upload.dir:/uploads/videos}")
    private String uploadDir;

    public VideoService(VideoRepository videoRepository, LessonRepository lessonRepository) {
        this.videoRepository = videoRepository;
        this.lessonRepository = lessonRepository;
    }

    // ---------------- Add video ----------------
    public Video addVideoToLesson(Long lessonId, String title, String url,
                                  Integer durationSeconds, Integer orderIndex, Boolean preview) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        Video video = new Video(title, url, durationSeconds, orderIndex, preview, lesson);
        return videoRepository.save(video);
    }

    // ---------------- Update video ----------------
    public Video update(Long id, String title, String url, Integer durationSeconds,
                        Integer orderIndex, Boolean preview) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        if (title != null) video.setTitle(title);
        if (url != null) video.setUrl(url);
        if (durationSeconds != null) video.setDurationSeconds(durationSeconds);
        if (orderIndex != null) video.setOrderIndex(orderIndex);
        if (preview != null) video.setPreview(preview);
        return videoRepository.save(video);
    }

    // ---------------- Delete video ----------------
    public void delete(Long id) {
        videoRepository.deleteById(id);
    }

    // ---------------- Get video ----------------
    public VideoDto getVideoDto(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        return toDto(video);
    }

    // ---------------- List videos by lesson ----------------
    public List<VideoDto> listByLessonDto(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        List<Video> videos = videoRepository.findByLessonOrderByOrderIndexAscIdAsc(lesson);
        return videos.stream().map(this::toDto).collect(Collectors.toList());
    }

    // ---------------- Store uploaded video ----------------
//    public String storeVideoFile(MultipartFile file) throws IOException {
//        File dir = new File(uploadDir);
//        if (!dir.exists()) dir.mkdirs();
//
//        String extension = "";
//        String originalName = file.getOriginalFilename();
//        if (originalName != null && originalName.contains(".")) {
//            extension = originalName.substring(originalName.lastIndexOf("."));
//        }
//        String filename = UUID.randomUUID() + extension;
//
//        File destination = new File(dir, filename);
//        file.transferTo(destination);
//
//        return "/uploads/videos/" + filename;
//    }
    
    public String storeVideoFile(MultipartFile file) throws IOException {
       
       String folder = System.getProperty("user.dir") + "/uploads/videos/";
        
        File dir = new File(folder);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            System.out.println("Created upload directory: " + created);
        }

        // Generate unique filename with original name preserved
        String originalName = file.getOriginalFilename();
        String filename = UUID.randomUUID() + "_" + (originalName != null ? originalName : "video.mp4");

        File destination = new File(dir, filename);
        System.out.println("Saving file to: " + destination.getAbsolutePath());

        // Save the file safely
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        // Return public URL for frontend access
        return "/uploads/videos/" + filename;

    }




    // ---------------- Convert Video to VideoDto ----------------
    private VideoDto toDto(Video video) {
        return new VideoDto(
            video.getId(),
            video.getTitle(),
            video.getUrl(),
            video.getDurationSeconds(),
            video.getOrderIndex(),
            video.getPreview()
        );
    }

}
