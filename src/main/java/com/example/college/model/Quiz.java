package com.example.college.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnore // Prevent circular reference or lazy loading issues
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    private boolean published = false;

    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<QuizQuestion> questions = new ArrayList<>();

    public Quiz() {}

    public Quiz(String title, Course course, User createdBy) {
        this.title = title;
        this.course = course;
        this.createdBy = createdBy;
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public User getCreatedBy() { return createdBy; }

    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public boolean isPublished() { return published; }

    public void setPublished(boolean published) { this.published = published; }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public List<QuizQuestion> getQuestions() { return questions; }

    public void setQuestions(List<QuizQuestion> questions) { this.questions = questions; }
}
