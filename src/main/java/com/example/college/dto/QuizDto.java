package com.example.college.dto;

import com.example.college.model.Quiz;

public class QuizDto {
	
	 public Long id;
	    public String title;
	    public boolean published;
	    public String courseTitle;

	    public QuizDto(Quiz quiz) {
	        this.id = quiz.getId();
	        this.title = quiz.getTitle();
	        this.published = quiz.isPublished();
	        this.courseTitle = quiz.getCourse().getTitle(); // make sure course is initialized
	    }

}
