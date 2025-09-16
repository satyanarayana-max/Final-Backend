package com.example.college.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coding_submissions")
public class CodingSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private CodingQuestion question;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @Column(columnDefinition = "TEXT")
    private String submittedCode;

    private int score; // marks gained for this submission

    private int totalTestCases;   // total test cases for the question
    private int passedTestCases;  // how many test cases passed

    private boolean isCorrect;    // true if all test cases passed

    private LocalDateTime submittedAt;

    // ---- Constructors ----
    public CodingSubmission() {}

    public CodingSubmission(Long id, CodingQuestion question, User student, String submittedCode,
                            int score, int totalTestCases, int passedTestCases,
                            boolean isCorrect, LocalDateTime submittedAt) {
        this.id = id;
        this.question = question;
        this.student = student;
        this.submittedCode = submittedCode;
        this.score = score;
        this.totalTestCases = totalTestCases;
        this.passedTestCases = passedTestCases;
        this.isCorrect = isCorrect;
        this.submittedAt = submittedAt;
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

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public String getSubmittedCode() {
        return submittedCode;
    }

    public void setSubmittedCode(String submittedCode) {
        this.submittedCode = submittedCode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalTestCases() {
        return totalTestCases;
    }

    public void setTotalTestCases(int totalTestCases) {
        this.totalTestCases = totalTestCases;
    }

    public int getPassedTestCases() {
        return passedTestCases;
    }

    public void setPassedTestCases(int passedTestCases) {
        this.passedTestCases = passedTestCases;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    // ---- toString() ----
    @Override
    public String toString() {
        return "CodingSubmission{" +
                "id=" + id +
                ", question=" + (question != null ? question.getId() : null) +
                ", student=" + (student != null ? student.getId() : null) +
                ", score=" + score +
                ", totalTestCases=" + totalTestCases +
                ", passedTestCases=" + passedTestCases +
                ", isCorrect=" + isCorrect +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
