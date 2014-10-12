package com.sunriseprojects.memorizer.web.rest.model;

import java.util.List;

import com.sunriseprojects.memorizer.core.dao.QuestionDAO;

public class QuizModel {

	private String uniqueId; 
	private List<QuestionDAO> questionList;
	
	private String totalCount;
	private String totalCorrect;
	private String average;
	
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getTotalCorrect() {
		return totalCorrect;
	}
	public void setTotalCorrect(String totalCorrect) {
		this.totalCorrect = totalCorrect;
	}
	public String getAverage() {
		return average;
	}
	public void setAverage(String average) {
		this.average = average;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public List<QuestionDAO> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<QuestionDAO> questionList) {
		this.questionList = questionList;
	}
	
	
	
}
