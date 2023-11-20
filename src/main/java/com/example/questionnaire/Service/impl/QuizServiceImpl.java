package com.example.questionnaire.Service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.questionnaire.Service.ifs.QuizService;
import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.repository.QusetionDao;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuestionnaireDao qnDao;
	@Autowired
	private QusetionDao quDao;

	// 新增
	@Transactional
	@Override
	public QuizRes create(QuizReq req) {
		QuizRes checkResult = checkData(req);
		if (checkResult != null) {
			return checkResult;
		}
		int id =qnDao.save(req.getQuestionnaire()).getId();
		List<Question> quList = req.getQuestion();
		// 如果他是空陣列 下面的程式碼就不用做了~
		// 因為可以新增問卷，但題目可以先不用新增
		if (quList.isEmpty()) {
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
	
		// 因為是List所以要遍歷 讓他存進去

		for (Question qu : quList) {
			qu.setQnId(id);
		}

		// 抓到資料後，就從原本的SAVE改成SAVEALL
		quDao.saveAll(quList);
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	// 抽方法~
	private QuizRes checkData(QuizReq req) {

		// 第一個table Questionnaire的防呆
		Questionnaire qn = req.getQuestionnaire();
		if (!StringUtils.hasText(qn.getTitle()) || !StringUtils.hasText(qn.getDescription())
				|| qn.getStartTime() == null || qn.getEndTime() == null || qn.getStartTime().isAfter(qn.getEndTime())) {
			return new QuizRes(RtnCode.QUSETIONNAIRE_DATA_ERROR);
		}

		// 第二個table Question的防呆
		List<Question> quList = req.getQuestion();
		// 因為是LIST所以要對每個項目去檢查
		for (Question qu : quList) {
			// int 判斷用小於等於0
			if (qu.getQuid() <= 0 || !StringUtils.hasText(qu.getqTitle()) || !StringUtils.hasText(qu.getOptionType())
					|| !StringUtils.hasText(qu.getOption())) {
				return new QuizRes(RtnCode.QUSETION_DATA_ERROR);
			}
		}

		return null;
	}

	// 修改
	@Transactional
	@Override
	public QuizRes update(QuizReq req) {
		// 參數檢查(私有方法的)
		QuizRes checkResult = checkData(req);
		if (checkResult != null) {
			return checkResult;
		}
		QuizRes checkQID = checkID(req);
		if (checkQID != null) {
			return checkQID;
		}
		Optional<Questionnaire> qnOp = qnDao.findById(req.getQuestionnaire().getId());
		if (qnOp.isEmpty()) {
			return new QuizRes(RtnCode.QUSETIONNAIRE_ID_NOT_FOUND);
		}

		// 修改問卷的判斷條件
		Questionnaire qn = qnOp.get();
		// is_publish==false可以改
		// is_publish==true 但當前時間要小於startdate(開始時間)
		if (!qn.isPublish() || (qn.isPublish() && LocalDate.now().isBefore(qn.getStartTime()))) {
			qnDao.save(req.getQuestionnaire());
			quDao.saveAll(req.getQuestion());
			return new QuizRes(RtnCode.SUCCESSFUL);
		}

		return new QuizRes(RtnCode.UPDATE_ERROR);
	}

	// 抽方法=>檢查ID的~~
	private QuizRes checkID(QuizReq req) {
		// 檢查id有沒有存在 因為在create沒有檢查(是AI的關係，資料庫會自動生成)~~ 但是在update時應該要存在了
		if (req.getQuestionnaire().getId() <= 0) {
			return new QuizRes(RtnCode.QUSETIONNAIRE_ID_DATA_ERROR);
		}
		List<Question> quList = req.getQuestion();
		// 判斷QNID是不是Questionnaire的ID=>外來鍵的概念!!
		for (Question qu : quList) {
			if (qu.getQnId() != req.getQuestionnaire().getId()) {
				return new QuizRes(RtnCode.QUSETIONNAIRE_ID_DATA_ERROR);
			}
		}
		return null;

	}
	@Transactional
	@Override
	public QuizRes deleteQuestionnaire(List<Integer> qnIdList) {
		List<Questionnaire> qnList = qnDao.findByIdIn(qnIdList);
		List<Integer> idList = new ArrayList();
		for (Questionnaire qn : qnList) {
			if (!qn.isPublish() || qn.isPublish() && LocalDate.now().isBefore(qn.getStartTime())) {
//				qnDao.deleteById(qn.getId());
				idList.add(qn.getId());
			}
		}
		if (!idList.isEmpty()) {
			qnDao.deleteAllById(idList);
			// 記得刪問卷的題目，不然只留題目，這個題目就會變成髒資料
			quDao.deleteByQnIdIn(idList);
		}

		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	@Override
	public QuizRes deleteQuestion(int qnId, List<Integer> quIdList) {
		Optional<Questionnaire> qnOp = qnDao.findById(qnId);
		if(qnOp.isEmpty()) {
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		Questionnaire op =qnOp.get();
		if(!op.isPublish()||op.isPublish()&& LocalDate.now().isBefore(op.getStartTime())) {
//			quDao.deleteByQnIdIn(quIdList);
			quDao.deleteByQnIdAndQuidIn(qnId,quIdList);
		}
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	@Override
	public QuizRes search(String title, LocalDate startTime, LocalDate endTime) {	
		title=StringUtils.hasText(title) ? title : "";	
		startTime =startTime!= null ? startTime : LocalDate.of(1900, 1, 1);
		endTime =endTime !=null? endTime:LocalDate.of(2900, 1, 1);		
		List<Questionnaire> qnList = qnDao.findByTitleContainingAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(title, startTime, endTime);
		//從Questionnaire找到的ID放到List裡面
		List<Integer> qnIds=new ArrayList<>();		
		for(Questionnaire item:qnList) {
			qnIds.add(item.getId());
		}
		//從Question抓出qnIds有哪些 (qnIds是foreign key )
		List<Question> quList=quDao.findAllByQnIdIn(qnIds);
		List<QuizVo> quizVoList=new ArrayList<>();		
		for(Questionnaire item:qnList) {
			//給他一個容器 裝問卷+問卷的問題
			QuizVo vo=new QuizVo();	
			vo.setQuestionnaire(item);
//			給他一個容器，裝有那些Qnid
			List<Question> questionList=new ArrayList<Question>();
			for(Question qu:quList) {		
				//Questionnaire的id是否等於Qusetion的qnid
				if(qu.getQnId()== item.getId()) {
					questionList.add(qu);
				}
			}			
			vo.setQuestion(questionList);
			quizVoList.add(vo);
			
		}
		return new QuizRes(quizVoList, RtnCode.SUCCESSFUL);
	}

	@Override
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startTime, LocalDate endTime) {
		title=StringUtils.hasText(title) ? title : "";	
		startTime =startTime!= null ? startTime : LocalDate.of(1900, 1, 1);
		endTime =endTime !=null? endTime:LocalDate.of(2900, 1, 1);		
		List<Questionnaire> qnList = qnDao.findByTitleContainingAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(title, startTime, endTime);
		
		return new QuestionnaireRes(qnList,RtnCode.SUCCESSFUL);
	}

	@Override
	public QuestionRes searchQuestionList(int qnId) {
		if(qnId<=0) {
			return new QuestionRes(null,RtnCode.QUSETIONNAIRE_ID_DATA_ERROR);
		}
//		找存不存在 List 也包含一個
		List<Question> quList=quDao.findAllByQnIdIn(Arrays.asList(qnId));
		return new QuestionRes(quList,RtnCode.SUCCESSFUL);
	}

	@Override
	public QuestionnaireRes searchQuestionnaireListFront(String title, LocalDate startTime, LocalDate endTime,
			boolean is_Published) {
		title=StringUtils.hasText(title) ? title : "";	
		startTime =startTime!= null ? startTime : LocalDate.of(1900, 1, 1);
		endTime =endTime !=null? endTime:LocalDate.of(2900, 1, 1);		
		List<Questionnaire> qnList=new ArrayList();
		if(is_Published) {
		qnList = qnDao.findByTitleContainingAndStartTimeGreaterThanEqualAndEndTimeLessThanEqualAndPublishTrue(title, startTime, endTime);
		}else {
		 qnList = qnDao.findByTitleContainingAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(title, startTime, endTime);
		}
				
		return new QuestionnaireRes(qnList,RtnCode.SUCCESSFUL);
		
	}







	
	

}
