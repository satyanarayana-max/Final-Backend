package com.example.college.dto;

import java.util.List;

public class CourseOutlineDto {
    public Long id;
    public String title;
    public String description;
    public String thumbnailUrl; // add this
    public String bannerUrl;    // add this
    public List<LessonDto> lessons;
    
//    public List<QuizDto> Quiz;
    

    public CourseOutlineDto(Long id, String title, String description, String thumbnailUrl, String bannerUrl, List<LessonDto> lessons) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.bannerUrl = bannerUrl;
        this.lessons = lessons;
    }
}
