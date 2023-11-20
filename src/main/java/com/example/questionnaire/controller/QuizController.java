package com.example.questionnaire.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.Service.ifs.QuizService;
import com.example.questionnaire.vo.QuizDeleteReq;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizSearchReq;

@RestController
public class QuizController {
		@Autowired
		private QuizService service;
		
		@PostMapping(value = "api/quiz/create")
		public QuizRes create(@RequestBody QuizReq req) {
			return service.create(req);
					}
		
		@GetMapping(value = "/api/quiz/search")
		private  QuizRes search(@RequestBody QuizSearchReq searchReq ) {
			return service.search(searchReq.getTitle(),searchReq.getStartTime(),searchReq.getEndTime());
			
		}
		
		@PostMapping(value="/api/quiz/delete")
		private  QuizRes deleteQuestionnaire(@RequestBody QuizDeleteReq qnIdList) {
			return service.deleteQuestionnaire(qnIdList.getQnIdList());
			
		}
		
}
