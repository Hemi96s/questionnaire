package com.example.questionnaire.constants;

import javax.management.loading.PrivateClassLoader;

public enum TheState {
	
	PUBLIC(true,"�}��"),
	PRIVATE(false,"���}��");
	
	private boolean Questionsatate;
	private String message;
	public boolean isQuestionsatate() {
		return Questionsatate;
	}
	public void setQuestionsatate(boolean questionsatate) {
		Questionsatate = questionsatate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private TheState(boolean questionsatate, String message) {
		Questionsatate = questionsatate;
		this.message = message;
	}
	
	
}
