package com.example.college.controller;

import com.example.college.dto.VideoDto;
import com.example.college.model.Video;
import com.example.college.service.VideoService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // ---------------- Upload Video File ----------------
    @PostMapping("/lessons/{lessonId}/videos/upload")
    public Video uploadLessonVideo(@PathVariable("lessonId") Long lessonId,
                                   @RequestParam("file") MultipartFile file) {
        try {
            System.out.println("Received file: " + file.getOriginalFilename());
            System.out.println("Size: " + file.getSize());

            String videoUrl = videoService.storeVideoFile(file);
            return videoService.addVideoToLesson(
                    lessonId,
                    file.getOriginalFilename(),
                    videoUrl,
                    null,
                    0,
                    false
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload video file", e);
        }
    }

    // ---------------- Add YouTube Video ----------------
    @PostMapping("/lessons/{lessonId}/videos/youtube")
    public Video addYoutubeVideo(@PathVariable Long lessonId,
                                 @RequestBody Map<String, String> body) {
        String url = body.get("url");
        if (url == null || url.isBlank()) {
            throw new RuntimeException("YouTube URL is required");
        }

        String title = body.getOrDefault("title", "YouTube Video");
        return videoService.addVideoToLesson(lessonId, title, url, null, 0, false);
    }

    // ---------------- List Videos by Lesson ----------------
    @GetMapping("/lessons/{lessonId}/videos")
    public List<VideoDto> listByLesson(@PathVariable Long lessonId) {
        return videoService.listByLessonDto(lessonId);
    }

    // ---------------- Update Video ----------------
    @PutMapping("/videos/{id}")
    public Video updateVideo(@PathVariable Long id,
                             @RequestBody Map<String, Object> body) {
        String title = (String) body.get("title");
        String url = (String) body.get("url");
        Integer duration = body.get("durationSeconds") instanceof Integer ? (Integer) body.get("durationSeconds") : null;
        Integer orderIndex = body.get("orderIndex") instanceof Integer ? (Integer) body.get("orderIndex") : null;
        Boolean preview = body.get("preview") instanceof Boolean ? (Boolean) body.get("preview") : null;

        return videoService.update(id, title, url, duration, orderIndex, preview);
    }

    // ---------------- Delete Video ----------------
    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------- Serve Video File ----------------
    @GetMapping("/media/videos/{filename}")
    public ResponseEntity<Resource> getVideo(@PathVariable String filename) throws IOException {
        Path path = Paths.get(System.getProperty("user.dir"), "uploads", "videos", filename);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
        		.contentType(MediaType.valueOf("video/mp4"))

                .body(resource);
    }
}
