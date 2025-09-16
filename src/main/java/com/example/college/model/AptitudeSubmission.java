package com.example.college.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.example.college.model.AptitudeQuestion;


@Entity
@Table(name = "aptitude_submissions")
public class AptitudeSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private AptitudeQuestion question;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    private String chosenOption;

    private boolean isCorrect;

    private int score;

    private LocalDateTime submittedAt;

    // ---- Constructors ----
    public AptitudeSubmission() {}

    public AptitudeSubmission(Long id, AptitudeQuestion question, User student, 
                              String chosenOption, boolean isCorrect, int score, 
                              LocalDateTime submittedAt) {
        this.id = id;
        this.question = question;
        this.student = student;
        this.chosenOption = chosenOption;
        this.isCorrect = isCorrect;
        this.score = score;
        this.submittedAt = submittedAt;
    }

    // ---- Getters & Setters ----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AptitudeQuestion getQuestion() {
        return question;
    }

    public void setQuestion(AptitudeQuestion question) {
        this.question = question;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public String getChosenOption() {
        return chosenOption;
    }

    public void setChosenOption(String chosenOption) {
        this.chosenOption = chosenOption;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
        return "AptitudeSubmission{" +
                "id=" + id +
                ", question=" + (question != null ? question.getId() : null) +
                ", student=" + (student != null ? student.getId() : null) +
                ", chosenOption='" + chosenOption + '\'' +
                ", isCorrect=" + isCorrect +
                ", score=" + score +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
