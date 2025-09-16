// TestCaseDTO.java
package com.example.college.dto;

public class TestCaseDTO {

    private String inputData;
    private String expectedOutput;
    private boolean hidden;

    public TestCaseDTO() {}

    public TestCaseDTO(String inputData, String expectedOutput, boolean hidden) {
        this.setInputData(inputData);
        this.setExpectedOutput(expectedOutput);
        this.setHidden(hidden);
    }

	public String getInputData() {
		return inputData;
	}

	public void setInputData(String inputData) {
		this.inputData = inputData;
	}

	public String getExpectedOutput() {
		return expectedOutput;
	}

	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

    
}
