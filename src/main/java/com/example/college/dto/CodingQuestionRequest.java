// CodingQuestionRequest.java
package com.example.college.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.List;


public class CodingQuestionRequest {

    private String title;
    private String description;
    private String topic;
    private String difficulty;
    private String sampleInput;
    private String sampleOutput;

    @JsonProperty("testCases")
    @NotNull
    private List<TestCaseDTO> testCases;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getSampleInput() {
		return sampleInput;
	}

	public void setSampleInput(String sampleInput) {
		this.sampleInput = sampleInput;
	}

	public String getSampleOutput() {
		return sampleOutput;
	}

	public void setSampleOutput(String sampleOutput) {
		this.sampleOutput = sampleOutput;
	}

	public CodingQuestionRequest(String title, String description, String topic, String difficulty, String sampleInput,
			String sampleOutput, @NotNull List<TestCaseDTO> testCases) {
		super();
		this.title = title;
		this.description = description;
		this.topic = topic;
		this.difficulty = difficulty;
		this.sampleInput = sampleInput;
		this.sampleOutput = sampleOutput;
		this.testCases = testCases;
	}

	public List<TestCaseDTO> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<TestCaseDTO> testCases) {
		this.testCases = testCases;
	}

	@Override
	public String toString() {
		return "CodingQuestionRequest [title=" + title + ", description=" + description + ", topic=" + topic
				+ ", difficulty=" + difficulty + ", sampleInput=" + sampleInput + ", sampleOutput=" + sampleOutput
				+ ", testCases=" + testCases + "]";
	}

    
}
