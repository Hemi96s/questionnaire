package com.example.questionnaire.vo;

import javax.persistence.Column;
import javax.persistence.Id;

public class QReq {

	private int quid;

	private int qnId;

	private String qTitle;

	private String optionType;
	

	private boolean Necessary;

	private String option;

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

	public String getqTitle() {
		return qTitle;
	}

	public void setqTitle(String qTitle) {
		this.qTitle = qTitle;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public boolean isNecessary() {
		return Necessary;
	}

	public void setNecessary(boolean necessary) {
		Necessary = necessary;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	

}
