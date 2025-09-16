package com.example.college.model;

import jakarta.persistence.*;

@Entity
@Table(name = "leaderboard")
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id", unique = true)
    private User student;

    @Column(nullable = false)
    private int codingScore;

    @Column(nullable = false)
    private int aptitudeScore;

    @Column(nullable = false)
    private int quizScore;

    @Column(nullable = false)
    private int totalScore;

    // --- Constructors ---
    public Leaderboard() {}

    public Leaderboard(User student, int codingScore, int aptitudeScore, int quizScore) {
        this.student = student;
        this.codingScore = codingScore;
        this.aptitudeScore = aptitudeScore;
        this.quizScore = quizScore;
        this.totalScore = codingScore + aptitudeScore + quizScore;
    }

    // --- Getters & Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public int getCodingScore() {
        return codingScore;
    }

    public void setCodingScore(int codingScore) {
        this.codingScore = codingScore;
        recalculateTotal();
    }

    public int getAptitudeScore() {
        return aptitudeScore;
    }

    public void setAptitudeScore(int aptitudeScore) {
        this.aptitudeScore = aptitudeScore;
        recalculateTotal();
    }

    public int getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(int quizScore) {
        this.quizScore = quizScore;
        recalculateTotal();
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    // --- Helper ---
    private void recalculateTotal() {
        this.totalScore = codingScore + aptitudeScore + quizScore;
    }
}
