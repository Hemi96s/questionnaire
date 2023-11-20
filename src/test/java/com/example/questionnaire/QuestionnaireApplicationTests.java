package com.example.questionnaire;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.questionnaire.repository.QuestionnaireDao;

@SpringBootTest
class QuestionnaireApplicationTests {
	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Test
	public void contextLoads() {
		

	}

}
