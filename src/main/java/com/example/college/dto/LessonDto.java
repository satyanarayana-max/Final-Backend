package com.example.college.dto;

import java.util.List;

public class LessonDto {
    public Long id;
    public String title;
    public String description;
    public Integer orderIndex;
    public List<VideoDto> videos;

    public LessonDto(Long id, String title, String description, Integer orderIndex, List<VideoDto> videos) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
        this.videos = videos;
    }
}
