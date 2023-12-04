package com.example.questionnaire.repository;

import java.util.List;

import javax.persistence.criteria.From;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.QuestionId;

@Repository
public interface QusetionDao extends JpaRepository<Question, QuestionId>{
	
	public void deleteByQnIdIn(List<Integer> qnIdList);
	public void deleteByQnIdAndQuidIn(int qnId, List<Integer> quIdList);
	
	public List<Question> findAllByQnIdIn(List<Integer> qnIdList);
	
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM questionnaire.question where qn_id=?1" ,nativeQuery = true)
	public List<Question> question(int qnId);
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO questionnaire.question (qn_id,q_title,option_type,is_necessary,q_option) VALUES (?1,?2,?3,?4,?5)",nativeQuery = true)
	public int addQuestion(int qnId,String qTitle,String optionType,boolean necessary,String qOption);

	@Transactional
	@Modifying
	@Query(value="UPDATE questionnaire.question set q_title=?1,option_type=?2,is_necessary=?3,q_option=?4 where id=?5",nativeQuery = true)
	public int updateQuestion(String qTitle,String optionType,boolean necessary,String qOption,int quid);
}
