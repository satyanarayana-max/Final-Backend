package com.example.college.model;

import jakarta.persistence.*;

@Entity
@Table(name = "coding_test_cases")
public class CodingTestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    private CodingQuestion question;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String inputData;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String expectedOutput;

    @Column(name = "hidden", nullable = false)
    private boolean hidden; // Matches DTO field name and type

    // ---- Constructors ----
    public CodingTestCase() {}

    public CodingTestCase(Long id, CodingQuestion question, String inputData, String expectedOutput, boolean hidden) {
        this.id = id;
        this.question = question;
        this.inputData = inputData;
        this.expectedOutput = expectedOutput;
        this.hidden = hidden;
    }

    // ---- Getters & Setters ----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CodingQuestion getQuestion() {
        return question;
    }

    public void setQuestion(CodingQuestion question) {
        this.question = question;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    // ---- toString() ----
    @Override
    public String toString() {
        return "CodingTestCase{" +
                "id=" + id +
                ", question=" + (question != null ? question.getId() : null) +
                ", inputData='" + inputData + '\'' +
                ", expectedOutput='" + expectedOutput + '\'' +
                ", hidden=" + hidden +
                '}';
    }
}
