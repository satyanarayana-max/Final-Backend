package com.example.college.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="enrollments",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id","course_id"}))
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="student_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="course_id")
    private Course course;

    private Instant enrolledAt = Instant.now();

    public Enrollment(){}
    public Enrollment(User student, Course course){
        this.student = student; this.course = course;
    }

    public Long getId(){ return id; }
    public User getStudent(){ return student; }
    public void setStudent(User student){ this.student = student; }
    public Course getCourse(){ return course; }
    public void setCourse(Course course){ this.course = course; }
    public Instant getEnrolledAt(){ return enrolledAt; }
    public void setEnrolledAt(Instant enrolledAt){ this.enrolledAt = enrolledAt; }
}
