package com.example.college.dto;

import jakarta.validation.constraints.NotBlank;

public class CourseDtos {

    // ================== CREATE COURSE DTO ==================
    public static class CreateCourseRequest {
        @NotBlank public String title;
        public String description;
        public String category;
        public String thumbnailUrl; // or handle as Multipart later
        public String bannerUrl;    // or handle as Multipart later
    }

    // ================== UPDATE COURSE DTO ==================
    public static class UpdateCourseRequest {
        @NotBlank public String title;
        public String description;
        public String category;
        public String thumbnailUrl;
        public String bannerUrl;
    }
}
