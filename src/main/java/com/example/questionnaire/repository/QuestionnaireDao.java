package com.example.questionnaire.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.entity.QuizAll;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;

@Repository
public interface QuestionnaireDao extends JpaRepository<Questionnaire, Integer>{
	
	/**
	 * 
	 * 取得資料，倒序後使出現的是最後、最新的一筆資料
	 */

	public Questionnaire findTopByOrderByIdDesc();
	
	public List<Questionnaire> findByIdIn(List<Integer> idList);
	
	public List<Questionnaire> findByTitleContainingAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(String tile,LocalDate startTime,LocalDate endTime)
;	
	public List<Questionnaire> findByTitleContainingAndStartTimeGreaterThanEqualAndEndTimeLessThanEqualAndPublishTrue(String tile,LocalDate startTime,LocalDate endTime);
//	@Transactional
//	@Modifying
//	@Query(value = "select * from questionnaire.questionnaire left join questionnaire.question on questionnaire.questionnaire.id=questionnaire.question.qn_id where questionnaire.questionnaire.id=?1",nativeQuery = true)
//	public QuizRes findQ(int id);
//	
//	@Transactional
//	@Modifying
//	@Query(value="select * from questionnaire.questionnaire left join questionnaire.question on questionnaire.questionnaire.id=questionnaire.question.qn_id where questionnaire.is_publish=1 AND (?2 IS NULL OR questionnaire.title LIKE %?2%) AND (?3 IS NULL OR questionnaire.start_date >= ?3) AND (?4 IS NULL OR pquestionnaire.end_date <= ?4)",nativeQuery = true)
//	public List<Questionnaire> findFront(String tile,LocalDate startTime,LocalDate endTime);
	@Transactional
	@Modifying
	@Query(value="SELECT * FROM questionnaire.questionnaire WHERE (?1 IS NULL OR title LIKE %?1%) AND (?2 IS NULL OR start_date >= ?2) AND (?3 IS NULL OR end_date <= ?3)",nativeQuery = true)
	public List<Questionnaire>searchQ(String title,LocalDate starTime, LocalDate endTime);
	
	
	
}
