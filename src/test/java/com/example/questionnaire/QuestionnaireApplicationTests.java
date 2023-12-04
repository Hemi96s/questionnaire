package com.example.questionnaire;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.repository.QusetionDao;

@SpringBootTest
class QuestionnaireApplicationTests {
	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	@Autowired
	private QusetionDao questionDao;
	
	
  
}
