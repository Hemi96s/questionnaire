package com.example.questionnaire.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QuestionId implements Serializable{

	private int quid;
	
	private int qnId;

	public int getQuid() {
		return quid;
	}

	public void setQuid(int quid) {
		this.quid = quid;
	}

	public int getQnId() {
		return qnId;
	}

	public void setQnId(int qnId) {
		this.qnId = qnId;
	}

	public QuestionId(int quid, int qnId) {
		super();
		this.quid = quid;
		this.qnId = qnId;
	}

	public QuestionId() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
