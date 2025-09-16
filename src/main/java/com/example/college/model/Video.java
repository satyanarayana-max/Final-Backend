package com.example.college.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title; // For display (filename or YouTube title)

    @NotBlank
    @Column(length = 1000)
    private String url; // Stores YouTube URL or uploaded file name

    private Integer durationSeconds;
    private Integer orderIndex = 0;
    private Boolean preview = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    @JsonBackReference
    private Lesson lesson;

    public Video() {}

    // Constructor for both YouTube and uploaded files
    public Video(String title, String url, Integer durationSeconds, Integer orderIndex, Boolean preview, Lesson lesson) {
        this.title = title;
        this.url = url;
        this.durationSeconds = durationSeconds;
        this.orderIndex = orderIndex;
        this.preview = preview;
        this.lesson = lesson;
    }

    // ---------------- Getters & Setters ----------------
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Integer getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(Integer durationSeconds) { this.durationSeconds = durationSeconds; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public Boolean getPreview() { return preview; }
    public void setPreview(Boolean preview) { this.preview = preview; }

    public Lesson getLesson() { return lesson; }
    public void setLesson(Lesson lesson) { this.lesson = lesson; }
}
