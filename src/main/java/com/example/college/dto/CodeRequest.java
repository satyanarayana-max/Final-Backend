package com.example.college.dto;

import jakarta.validation.constraints.NotBlank;

public class CodeRequest {
	
	 @NotBlank
	    private String script;

	    @NotBlank
	    private String language;

	    private String versionIndex = "0"; // default compiler version
	    
	    private String stdin;

	    public String getStdin() {
			return stdin;
		}
		public void setStdin(String stdin) {
			this.stdin = stdin;
		}
		// Getters and Setters
	    public String getScript() { return script; }
	    public void setScript(String script) { this.script = script; }

	    public String getLanguage() { return language; }
	    public void setLanguage(String language) { this.language = language; }

	    public String getVersionIndex() { return versionIndex; }
	    public void setVersionIndex(String versionIndex) { this.versionIndex = versionIndex; }

}
