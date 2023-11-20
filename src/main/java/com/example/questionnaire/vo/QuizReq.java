package com.example.questionnaire.vo;

import java.util.List;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;

public class QuizReq  extends QuizVo{

	public QuizReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuizReq(Questionnaire questionnaire, List<Question> question) {
		super(questionnaire, question);
		// TODO Auto-generated constructor stub
	}
	
	
}
