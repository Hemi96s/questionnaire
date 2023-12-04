package com.example.questionnaire.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.Service.ifs.QuizService;
import com.example.questionnaire.constants.TheState;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.repository.QusetionDao;
import com.example.questionnaire.vo.QReq;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizDeleteReq;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizSearchReq;
import com.example.questionnaire.vo.QuizVo;

@RestController
@CrossOrigin(origins="http://localhost:5173")
public class QuizController {
		@Autowired
		private QuizService service;
		
		@Autowired
		private QuestionnaireDao questionnaireDao;
		
		@Autowired
		private QusetionDao questionDao;
		
		@PostMapping(value = "api/quiz/create")
		public QuizRes create(@RequestBody QuizReq req) {
			return service.create(req);
					}
		
		@GetMapping(value = "/api/quiz/search")
		public  QuizRes search(@RequestBody QuizSearchReq searchReq ) {
			String title=StringUtils.hasText(searchReq.getTitle()) ? searchReq.getTitle() : "";	
			LocalDate startTime =searchReq.getStartTime()!= null ? searchReq.getStartTime() : LocalDate.of(1900, 1, 1);
			LocalDate endTime =searchReq.getEndTime() !=null? searchReq.getEndTime():LocalDate.of(2900, 1, 1);		
			return service.search(searchReq.getTitle(),searchReq.getStartTime(),searchReq.getEndTime());
			
		}
		
		@PostMapping(value="/api/quiz/delete")
		public  QuizRes deleteQuestionnaire(@RequestBody QuizDeleteReq qnIdList) {
			return service.deleteQuestionnaire(qnIdList.getQnIdList());
			
		};
		
		//�j�M�ݨ� (id�B�ݨ����D�B���A�B�}�l�B����)
		@PostMapping(value="quitionnaire/search")
		public List<Questionnaire> searchALl(@RequestBody QuizSearchReq searchReq) {
			 return questionnaireDao.searchQ(searchReq.getTitle(), searchReq.getStartTime(), searchReq.getEndTime());
			
			
		};
		
		//���դ@�U=>OK ��ID��X�Ҧ�ID���D��
		@PostMapping(value="findQiestion")
		public List<Question> findQiestion(@RequestBody QReq www){
			return questionDao.question(www.getQnId());
		}
		
		//��X�Ҧ����ݨ�
		@GetMapping(value="getAll")
		public List<Questionnaire> getAll(){

			return questionnaireDao.findAll();
		}
		
		//��X���A���벼�����ݨ�
		
		//�ק�ݨ��D�ػP����
		@PostMapping(value="updateQuestion")
		public int updateQiestion(@RequestBody QReq www) {
			return questionDao.updateQuestion(www.getqTitle(), www.getOptionType(), www.isNecessary(), www.getOption(), www.getQuid());
		}
		
		//�s�W���D�ػP����
		@PostMapping(value="createQuestion")
		public int createQiestion(@RequestBody QReq www) {
			return questionDao.addQuestion(www.getQnId(),www.getqTitle(), www.getOptionType(), www.isNecessary(), www.getOption());
		}
}
