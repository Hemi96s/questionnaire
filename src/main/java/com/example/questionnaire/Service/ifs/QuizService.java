package com.example.questionnaire.Service.ifs;

import java.time.LocalDate;
import java.util.List;

import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;

public interface QuizService {
	
	public QuizRes create(QuizReq req) ;
	
	public QuizRes update(QuizReq req) ;
	
	public QuizRes deleteQuestionnaire(List<Integer> qnIdList);
	
	public QuizRes deleteQuestion(int qnId,List<Integer> quIdList);
	
	public QuizRes search(String title,LocalDate startTime ,LocalDate endTime);
	

	
	public QuestionnaireRes searchQuestionnaireList(String title,LocalDate startTime ,LocalDate endTime);
	
	public QuestionRes searchQuestionList(int qnId);
	
	public QuestionnaireRes searchQuestionnaireListFront(String title,LocalDate startTime ,LocalDate endTime,boolean is_Published);
}
