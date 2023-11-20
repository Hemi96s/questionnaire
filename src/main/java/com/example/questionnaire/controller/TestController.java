package com.example.questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.vo.QuizRes;

@RestController
public class TestController {
@Autowired 
private QuestionnaireDao questionnaireDao;
}