package com.example.questionnaire.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
@Entity
@Table(name="questionnaire")

public class Questionnaire {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")	
	private int id;

	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="is_publish")
	private boolean Publish;
	
	@Column(name="start_date")
	private LocalDate startTime;
	
	@Column(name="end_date")
	private LocalDate endTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublish() {
		return Publish;
	}

	public void setPublish(boolean publish) {
		Publish = publish;
	}

	public LocalDate getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDate startTime) {
		this.startTime = startTime;
	}

	public LocalDate getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDate endTime) {
		this.endTime = endTime;
	}

	public Questionnaire() {
		super();
	}

	public Questionnaire(String title, String description, boolean publish, LocalDate startTime, LocalDate endTime) {
		super();
		this.title = title;
		this.description = description;
		Publish = publish;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	
}
