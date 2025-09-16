package com.example.college.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CodingDTOs {

    // === CodingQuestionRequest ===
    public static class CodingQuestionRequest {
        private String title;
        private String description;
        private String topic;
        private String difficulty;
        private String sampleInput;
        private String sampleOutput;

        @JsonProperty("testCases")
        @NotNull(message = "Test cases must not be null")
        @Size(min = 1, message = "Test cases must not be empty")
        private List<TestCaseDTO> testCases;

        public CodingQuestionRequest() {}

        public CodingQuestionRequest(String title, String description, String topic, String difficulty,
                                     String sampleInput, String sampleOutput, List<TestCaseDTO> testCases) {
            this.title = title;
            this.description = description;
            this.topic = topic;
            this.difficulty = difficulty;
            this.sampleInput = sampleInput;
            this.sampleOutput = sampleOutput;
            this.testCases = testCases;
        }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getTopic() { return topic; }
        public void setTopic(String topic) { this.topic = topic; }

        public String getDifficulty() { return difficulty; }
        public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

        public String getSampleInput() { return sampleInput; }
        public void setSampleInput(String sampleInput) { this.sampleInput = sampleInput; }

        public String getSampleOutput() { return sampleOutput; }
        public void setSampleOutput(String sampleOutput) { this.sampleOutput = sampleOutput; }

        public List<TestCaseDTO> getTestCases() { return testCases; }
        public void setTestCases(List<TestCaseDTO> testCases) { this.testCases = testCases; }
    }

    // === TestCaseDTO ===
    public static class TestCaseDTO {
        private String inputData;
        private String expectedOutput;
        private boolean hidden;

        public TestCaseDTO() {}

        public TestCaseDTO(String inputData, String expectedOutput, boolean hidden) {
            this.inputData = inputData;
            this.expectedOutput = expectedOutput;
            this.hidden = hidden;
        }

        @Override
		public String toString() {
			return "TestCaseDTO [inputData=" + inputData + ", expectedOutput=" + expectedOutput + ", hidden=" + hidden
					+ "]";
		}

		public String getInputData() { return inputData; }
        public void setInputData(String inputData) { this.inputData = inputData; }

        public String getExpectedOutput() { return expectedOutput; }
        public void setExpectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; }

        public boolean isHidden() { return hidden; }
        public void setHidden(boolean hidden) { this.hidden = hidden; }
    }

    // === CodingQuestionResponse ===
    public static class CodingQuestionResponse {
        private Long id;
        private String title;
        private String description;
        private String topic;
        private String difficulty;
        private String sampleInput;
        private String sampleOutput;
        public List<TestCaseDTO> getTestCases() {
			return testCases;
		}

		public void setTestCases(List<TestCaseDTO> testCases) {
			this.testCases = testCases;
		}
		private List<TestCaseDTO> testCases;

        public CodingQuestionResponse() {}

        public CodingQuestionResponse(Long id, String title, String description, String topic, String difficulty,
                                      String sampleInput, String sampleOutput) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.topic = topic;
            this.difficulty = difficulty;
            this.sampleInput = sampleInput;
            this.sampleOutput = sampleOutput;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getTopic() { return topic; }
        public void setTopic(String topic) { this.topic = topic; }

        public String getDifficulty() { return difficulty; }
        public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

        public String getSampleInput() { return sampleInput; }
        public void setSampleInput(String sampleInput) { this.sampleInput = sampleInput; }

        public String getSampleOutput() { return sampleOutput; }
        public void setSampleOutput(String sampleOutput) { this.sampleOutput = sampleOutput; }
    }

    // === CodingSubmissionRequest ===
    public static class CodingSubmissionRequest {
        private Long questionId;
        private String code;
        private int totalTestCases;
        private int passedTestCases;
        private int score;
        private boolean correct;
       

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

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public boolean isCorrect() {
			return correct;
		}

		public void setCorrect(boolean correct) {
			this.correct = correct;
		}

		public CodingSubmissionRequest() {}

        public CodingSubmissionRequest(Long questionId, String code) {
            this.questionId = questionId;
            this.code = code;
        }

        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
    }

    // === CodingSubmissionResponse ===
    public static class CodingSubmissionResponse {
        private Long submissionId;
        private Long questionId;
        private int totalTestCases;
        private int passedTestCases;
        private int score;
        private boolean correct;

        public CodingSubmissionResponse() {}

        public CodingSubmissionResponse(Long submissionId, Long questionId, int totalTestCases,
                                        int passedTestCases, int score, boolean correct) {
            this.submissionId = submissionId;
            this.questionId = questionId;
            this.totalTestCases = totalTestCases;
            this.passedTestCases = passedTestCases;
            this.score = score;
            this.correct = correct;
        }

        public Long getSubmissionId() { return submissionId; }
        public void setSubmissionId(Long submissionId) { this.submissionId = submissionId; }

        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }

        public int getTotalTestCases() { return totalTestCases; }
        public void setTotalTestCases(int totalTestCases) { this.totalTestCases = totalTestCases; }

        public int getPassedTestCases() { return passedTestCases; }
        public void setPassedTestCases(int passedTestCases) { this.passedTestCases = passedTestCases; }

        public int getScore() { return score; }
        public void setScore(int score) { this.score = score; }

        public boolean isCorrect() { return correct; }
        public void setCorrect(boolean correct) { this.correct = correct; }
    }
}
