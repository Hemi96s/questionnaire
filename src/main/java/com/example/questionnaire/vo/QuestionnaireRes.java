package com.example.questionnaire.vo;

import java.util.List;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Questionnaire;

public class QuestionnaireRes {
	//因為撈出來是多筆，所以是用List
	private List<Questionnaire> Questionnaire;
	
	private RtnCode reCode;
	
	public List<Questionnaire> getQuestionnaire() {
		return Questionnaire;
	}
	
	public void setQuestionnaire(List<Questionnaire> questionnaire) {
		Questionnaire = questionnaire;
	}
	
	public RtnCode getReCode() {
		return reCode;
	}
	
	public void setReCode(RtnCode reCode) {
		this.reCode = reCode;
	}
	
	public QuestionnaireRes(List<com.example.questionnaire.entity.Questionnaire> questionnaire, RtnCode reCode) {
		super();
		Questionnaire = questionnaire;
		this.reCode = reCode;
	}
	
	public QuestionnaireRes() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
