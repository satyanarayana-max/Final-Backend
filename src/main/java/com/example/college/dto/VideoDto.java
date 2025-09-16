package com.example.college.dto;

public class VideoDto {
    public Long id;
    public String title;          // Display name (file name or YouTube title)
    public String url;            // YouTube URL or uploaded file path
    public Integer durationSeconds;
    public Integer orderIndex;
    public Boolean preview;

    public VideoDto(Long id, String title, String url,
                    Integer durationSeconds, Integer orderIndex, Boolean preview) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.durationSeconds = durationSeconds;
        this.orderIndex = orderIndex;
        this.preview = preview;
    }
}
