package com.example.questionnaire.entity;

public class QuizAll {
	
	private Questionnaire questionnaire;
	private Question question;
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public QuizAll(Questionnaire questionnaire, Question question) {
		super();
		this.questionnaire = questionnaire;
		this.question = question;
	}
	public QuizAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}

