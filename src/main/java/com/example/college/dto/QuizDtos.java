package com.example.college.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public class QuizDtos {

    // ---------------- Create Quiz ----------------
    public static class CreateQuizRequest {
        @NotBlank
        public String title;

        @NotNull
        public Long courseId;

        @NotNull
        public List<Question> questions;

        public Boolean published; // optional, default false
    }

    // ---------------- Update Quiz ----------------
    public static class UpdateQuizRequest {
        @NotBlank
        public String title;

        public List<Question> questions;

        public Boolean published; // can be updated
    }

    // ---------------- Question DTO ----------------
    public static class Question {
        public Long id; // optional, used for update

        @NotBlank
        public String question;

        @NotBlank
        public String optionA;

        @NotBlank
        public String optionB;

        @NotBlank
        public String optionC;

        @NotBlank
        public String optionD;

        @NotBlank
        public String correctOption;
    }

    // ---------------- Submit Quiz ----------------
    public static class SubmitQuizRequest {
        @NotNull
        public Long quizId;

        @NotNull
        public Map<Long, String> answers;
    }
}
