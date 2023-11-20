package com.example.questionnaire.vo;

import java.util.ArrayList;
import java.util.List;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;

public class QuizVo {
	private Questionnaire questionnaire= new Questionnaire();;
	private List<Question> Question  =new ArrayList<>();;
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}
	public List<Question> getQuestion() {
		return Question;
	}
	public void setQuestion(List<Question> question) {
		Question = question;
	}
	public QuizVo(Questionnaire questionnaire, List<com.example.questionnaire.entity.Question> question) {
		super();
		this.questionnaire = questionnaire;
		Question = question;
	}
	public QuizVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
