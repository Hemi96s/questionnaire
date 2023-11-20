package com.example.questionnaire.constants;

public enum RtnCode {

	SUCCESSFUL("200", "Successful!"),
	QUSETION_DATA_ERROR("400","Question data error!"),
	QUSETIONNAIRE_DATA_ERROR("400","Qusrtionnaire data error!"),
	QUSETIONNAIRE_ID_DATA_ERROR("400","Qusrtionnaire ID data error!"),
	QUSETIONNAIRE_ID_NOT_FOUND("404","Qusrtionnaire ID not found!"),
	UPDATE_ERROR("400","Update_error");

	private String code;
	private String message;

	// SUCCESSFUL蚯蚓=>私有建構方法
	private RtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	// getter&setter
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
