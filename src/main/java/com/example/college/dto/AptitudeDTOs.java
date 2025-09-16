package com.example.college.dto;

public class AptitudeDTOs {

    public static class AptitudeQuestionRequest {
        private String section;
        private String questionText;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctOption;
        private int marks;

        public AptitudeQuestionRequest() {}

        public AptitudeQuestionRequest(String section, String questionText, String optionA, String optionB,
                                       String optionC, String optionD, String correctOption, int marks) {
            this.section = section;
            this.questionText = questionText;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.correctOption = correctOption;
            this.marks = marks;
        }

        // Getters and Setters
        public String getSection() { return section; }
        public void setSection(String section) { this.section = section; }

        public String getQuestionText() { return questionText; }
        public void setQuestionText(String questionText) { this.questionText = questionText; }

        public String getOptionA() { return optionA; }
        public void setOptionA(String optionA) { this.optionA = optionA; }

        public String getOptionB() { return optionB; }
        public void setOptionB(String optionB) { this.optionB = optionB; }

        public String getOptionC() { return optionC; }
        public void setOptionC(String optionC) { this.optionC = optionC; }

        public String getOptionD() { return optionD; }
        public void setOptionD(String optionD) { this.optionD = optionD; }

        public String getCorrectOption() { return correctOption; }
        public void setCorrectOption(String correctOption) { this.correctOption = correctOption; }

        public int getMarks() { return marks; }
        public void setMarks(int marks) { this.marks = marks; }
    }

    public static class AptitudeQuestionResponse {
        private Long id;
        private String section;
        private String questionText;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctOption;
        private int marks;

        public AptitudeQuestionResponse() {}

        public AptitudeQuestionResponse(Long id, String section, String questionText, String optionA, String optionB,
                                        String optionC, String optionD, int marks) {
            this.id = id;
            this.section = section;
            this.questionText = questionText;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.marks = marks;
        }
        
        
        public AptitudeQuestionResponse(Long id, String section, String questionText, String optionA, String optionB,
                String optionC, String optionD,String correctOption, int marks) {
this.id = id;
this.section = section;
this.questionText = questionText;
this.optionA = optionA;
this.optionB = optionB;
this.optionC = optionC;
this.optionD = optionD;
this.correctOption=correctOption;
this.marks = marks;
}

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getSection() { return section; }
        public void setSection(String section) { this.section = section; }

        public String getQuestionText() { return questionText; }
        public void setQuestionText(String questionText) { this.questionText = questionText; }

        public String getOptionA() { return optionA; }
        public void setOptionA(String optionA) { this.optionA = optionA; }

        public String getOptionB() { return optionB; }
        public void setOptionB(String optionB) { this.optionB = optionB; }

        public String getOptionC() { return optionC; }
        public void setOptionC(String optionC) { this.optionC = optionC; }

        public String getOptionD() { return optionD; }
        public void setOptionD(String optionD) { this.optionD = optionD; }

        public int getMarks() { return marks; }
        public void setMarks(int marks) { this.marks = marks; }

		public String getCorrectOption() {
			return correctOption;
		}

		public void setCorrectOption(String correctOption) {
			this.correctOption = correctOption;
		}
        
        
        
    }

    public static class AptitudeSubmissionRequest {
        private Long questionId;
        private String chosenOption;

        public AptitudeSubmissionRequest() {}

        public AptitudeSubmissionRequest(Long questionId, String chosenOption) {
            this.questionId = questionId;
            this.chosenOption = chosenOption;
        }

        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }

        public String getChosenOption() { return chosenOption; }
        public void setChosenOption(String chosenOption) { this.chosenOption = chosenOption; }
    }

    public static class AptitudeSubmissionResponse {
        private Long submissionId;
        private Long questionId;
        private boolean correct;
        private int score;

        public AptitudeSubmissionResponse() {}

        public AptitudeSubmissionResponse(Long submissionId, Long questionId, boolean correct, int score) {
            this.submissionId = submissionId;
            this.questionId = questionId;
            this.correct = correct;
            this.score = score;
        }

        public Long getSubmissionId() { return submissionId; }
        public void setSubmissionId(Long submissionId) { this.submissionId = submissionId; }

        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }

        public boolean isCorrect() { return correct; }
        public void setCorrect(boolean correct) { this.correct = correct; }

        public int getScore() { return score; }
        public void setScore(int score) { this.score = score; }
    }
}
