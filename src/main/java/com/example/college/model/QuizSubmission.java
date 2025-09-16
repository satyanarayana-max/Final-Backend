package com.example.college.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="quiz_submissions", uniqueConstraints = @UniqueConstraint(columnNames={"quiz_id","student_id"}))
public class QuizSubmission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="quiz_id")
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="student_id")
    private User student;

    @Column(columnDefinition="TEXT")
    private String answersJson; // {"questionId":"A",...}

    private Integer score;

    private Instant submittedAt = Instant.now();

    public QuizSubmission(){}
    public QuizSubmission(Quiz quiz, User student, String answersJson, Integer score){
        this.quiz = quiz; this.student = student; this.answersJson = answersJson; this.score = score;
    }

    public Long getId(){ return id; }
    public Quiz getQuiz(){ return quiz; }
    public void setQuiz(Quiz quiz){ this.quiz = quiz; }
    public User getStudent(){ return student; }
    public void setStudent(User student){ this.student = student; }
    public String getAnswersJson(){ return answersJson; }
    public void setAnswersJson(String answersJson){ this.answersJson = answersJson; }
    public Integer getScore(){ return score; }
    public void setScore(Integer score){ this.score = score; }
    public Instant getSubmittedAt(){ return submittedAt; }
    public void setSubmittedAt(Instant submittedAt){ this.submittedAt = submittedAt; }
}
