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

	// �s�W
	@Transactional
	@Override
	public QuizRes create(QuizReq req) {
		QuizRes checkResult = checkData(req);
		if (checkResult != null) {
			return checkResult;
		}
		int id =qnDao.save(req.getQuestionnaire()).getId();
		List<Question> quList = req.getQuestion();
		// �p�G�L�O�Ű}�C �U�����{���X�N���ΰ��F~
		// �]���i�H�s�W�ݨ��A���D�إi�H�����ηs�W
		if (quList.isEmpty()) {
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
	
		// �]���OList�ҥH�n�M�� ���L�s�i�h

		for (Question qu : quList) {
			qu.setQnId(id);
		}

		// ����ƫ�A�N�q�쥻��SAVE�令SAVEALL
		quDao.saveAll(quList);
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	// ���k~
	private QuizRes checkData(QuizReq req) {

		// �Ĥ@��table Questionnaire�����b
		Questionnaire qn = req.getQuestionnaire();
		if (!StringUtils.hasText(qn.getTitle()) || !StringUtils.hasText(qn.getDescription())
				|| qn.getStartTime() == null || qn.getEndTime() == null || qn.getStartTime().isAfter(qn.getEndTime())) {
			return new QuizRes(RtnCode.QUSETIONNAIRE_DATA_ERROR);
		}

		// �ĤG��table Question�����b
		List<Question> quList = req.getQuestion();
		// �]���OLIST�ҥH�n��C�Ӷ��إh�ˬd
		for (Question qu : quList) {
			// int �P�_�Τp�󵥩�0
			if (qu.getQuid() <= 0 || !StringUtils.hasText(qu.getqTitle()) || !StringUtils.hasText(qu.getOptionType())
					|| !StringUtils.hasText(qu.getOption())) {
				return new QuizRes(RtnCode.QUSETION_DATA_ERROR);
			}
		}

		return null;
	}

	// �ק�
	@Transactional
	@Override
	public QuizRes update(QuizReq req) {
		// �Ѽ��ˬd(�p����k��)
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

		// �ק�ݨ����P�_����
		Questionnaire qn = qnOp.get();
		// is_publish==false�i�H��
		// is_publish==true ����e�ɶ��n�p��startdate(�}�l�ɶ�)
		if (!qn.isPublish() || (qn.isPublish() && LocalDate.now().isBefore(qn.getStartTime()))) {
			qnDao.save(req.getQuestionnaire());
			quDao.saveAll(req.getQuestion());
			return new QuizRes(RtnCode.SUCCESSFUL);
		}

		return new QuizRes(RtnCode.UPDATE_ERROR);
	}

	// ���k=>�ˬdID��~~
	private QuizRes checkID(QuizReq req) {
		// �ˬdid���S���s�b �]���bcreate�S���ˬd(�OAI�����Y�A��Ʈw�|�۰ʥͦ�)~~ ���O�bupdate�����ӭn�s�b�F
		if (req.getQuestionnaire().getId() <= 0) {
			return new QuizRes(RtnCode.QUSETIONNAIRE_ID_DATA_ERROR);
		}
		List<Question> quList = req.getQuestion();
		// �P�_QNID�O���OQuestionnaire��ID=>�~���䪺����!!
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
			// �O�o�R�ݨ����D�ءA���M�u�d�D�ءA�o���D�شN�|�ܦ�ż���
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
		//�qQuestionnaire��쪺ID���List�̭�
		List<Integer> qnIds=new ArrayList<>();		
		for(Questionnaire item:qnList) {
			qnIds.add(item.getId());
		}
		//�qQuestion��XqnIds������ (qnIds�Oforeign key )
		List<Question> quList=quDao.findAllByQnIdIn(qnIds);
		List<QuizVo> quizVoList=new ArrayList<>();		
		for(Questionnaire item:qnList) {
			//���L�@�Ӯe�� �˰ݨ�+�ݨ������D
			QuizVo vo=new QuizVo();	
			vo.setQuestionnaire(item);
//			���L�@�Ӯe���A�˦�����Qnid
			List<Question> questionList=new ArrayList<Question>();
			for(Question qu:quList) {		
				//Questionnaire��id�O�_����Qusetion��qnid
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
//		��s���s�b List �]�]�t�@��
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
