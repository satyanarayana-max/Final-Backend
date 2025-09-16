package com.example.college.dto;

public class LeaderboardDTO {

    private Long studentId;
    private String studentName;
    private int codingScore;
    private int aptitudeScore;
    private int quizScore;
    private int totalScore;

    // --- No-arg constructor ---
    public LeaderboardDTO() {}

    // --- All-arg constructor ---
    public LeaderboardDTO(Long studentId, String studentName, int codingScore, int aptitudeScore, int quizScore, int totalScore) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.codingScore = codingScore;
        this.aptitudeScore = aptitudeScore;
        this.quizScore = quizScore;
        this.totalScore = totalScore;
    }

    // --- Getters & Setters ---
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getCodingScore() {
        return codingScore;
    }

    public void setCodingScore(int codingScore) {
        this.codingScore = codingScore;
    }

    public int getAptitudeScore() {
        return aptitudeScore;
    }

    public void setAptitudeScore(int aptitudeScore) {
        this.aptitudeScore = aptitudeScore;
    }

    public int getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(int quizScore) {
        this.quizScore = quizScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    // --- Builder pattern ---
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long studentId;
        private String studentName;
        private int codingScore;
        private int aptitudeScore;
        private int quizScore;
        private int totalScore;

        public Builder studentId(Long studentId) {
            this.studentId = studentId;
            return this;
        }

        public Builder studentName(String studentName) {
            this.studentName = studentName;
            return this;
        }

        public Builder codingScore(int codingScore) {
            this.codingScore = codingScore;
            return this;
        }

        public Builder aptitudeScore(int aptitudeScore) {
            this.aptitudeScore = aptitudeScore;
            return this;
        }

        public Builder quizScore(int quizScore) {
            this.quizScore = quizScore;
            return this;
        }

        public Builder totalScore(int totalScore) {
            this.totalScore = totalScore;
            return this;
        }

        public LeaderboardDTO build() {
            return new LeaderboardDTO(studentId, studentName, codingScore, aptitudeScore, quizScore, totalScore);
        }
    }
}
