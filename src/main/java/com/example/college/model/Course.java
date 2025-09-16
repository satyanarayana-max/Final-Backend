package com.example.college.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.*;
import java.time.Instant;

@Entity
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    // Image fields
    private String thumbnailUrl;  // path or URL to thumbnail image
    private String bannerUrl;     // path or URL to banner image

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC, id ASC")
    @JsonManagedReference
  
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_id")
    private User teacher;

    private Instant createdAt = Instant.now();

    public Course() {}

    public Course(String title, String description, String category,
                  String thumbnailUrl, String bannerUrl, User teacher) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
        this.bannerUrl = bannerUrl;
        this.teacher = teacher;
    }

    // ----------------- GETTERS & SETTERS -----------------
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getBannerUrl() { return bannerUrl; }
    public void setBannerUrl(String bannerUrl) { this.bannerUrl = bannerUrl; }

    public List<Lesson> getLessons() { return lessons; }
    public void setLessons(List<Lesson> lessons) { this.lessons = lessons; }

    public User getTeacher() { return teacher; }
    public void setTeacher(User teacher) { this.teacher = teacher; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
