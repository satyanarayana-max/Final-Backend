package com.example.college.dto;

import java.util.List;

public class QuizAttemptDto {
    public Long quizId;
    public String title;
    public List<QuestionDto> questions;

    public static class QuestionDto {
        public Long id;
        public String question;
        public String optionA;
        public String optionB;
        public String optionC;
        public String optionD;
    }
}
