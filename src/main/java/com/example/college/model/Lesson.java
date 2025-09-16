package com.example.college.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description = "";

    private Integer orderIndex = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @OrderBy("orderIndex ASC, id ASC")
    private List<Video> videos = new ArrayList<>();

    public Lesson() {}

    public Lesson(String title, String description, Integer orderIndex, Course course) {
        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
        this.course = course;
    }

    // ----------------- GETTERS & SETTERS -----------------
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public List<Video> getVideos() { return videos; }
    public void setVideos(List<Video> videos) { this.videos = videos; }
}
