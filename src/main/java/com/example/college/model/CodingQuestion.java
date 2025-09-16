package com.example.college.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "coding_questions")
public class CodingQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String topic; // e.g. Arrays, Strings, DP

    private String difficulty; // Easy, Medium, Hard

    @Column(columnDefinition = "TEXT")
    private String sampleInput;

    @Column(columnDefinition = "TEXT")
    private String sampleOutput;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CodingTestCase> testCases;

    // ---- Constructors ----
    public CodingQuestion() {}

    public CodingQuestion(Long id, String title, String description, String topic, String difficulty,
                          String sampleInput, String sampleOutput, List<CodingTestCase> testCases) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.topic = topic;
        this.difficulty = difficulty;
        this.sampleInput = sampleInput;
        this.sampleOutput = sampleOutput;
        this.testCases = testCases;
    }

    // ---- Getters & Setters ----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getSampleInput() {
        return sampleInput;
    }

    public void setSampleInput(String sampleInput) {
        this.sampleInput = sampleInput;
    }

    public String getSampleOutput() {
        return sampleOutput;
    }

    public void setSampleOutput(String sampleOutput) {
        this.sampleOutput = sampleOutput;
    }

    public List<CodingTestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<CodingTestCase> testCases) {
        this.testCases = testCases;
    }

    // ---- toString() ----
    @Override
    public String toString() {
        return "CodingQuestion{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", topic='" + topic + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", sampleInput='" + sampleInput + '\'' +
                ", sampleOutput='" + sampleOutput + '\'' +
                '}';
    }
}
