package com.sunriseprojects.memorizer.web.rest.model;
 
import java.io.Serializable;
import java.util.Date;
 
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
 
public class QuestionModel implements Serializable{
 
    private static final long serialVersionUID = -7788619177798333713L;
     
    private String sessionId;
    private String question;
    Map<String, String> selection;
    private String memberNumber;
	private String status;
	private int number;
	
	private int totalQuestionRunningValue;
	private int totalQuestion;
	private int numberOfSetsDone;
	private int questionSetRunningValue;
	private int questionSetTotalValue;
	private String answer;
	private String explanation;
	
    public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public int getTotalQuestionRunningValue() {
		return totalQuestionRunningValue;
	}
	public void setTotalQuestionRunningValue(int totalQuestionRunningValue) {
		this.totalQuestionRunningValue = totalQuestionRunningValue;
	}
	public int getTotalQuestion() {
		return totalQuestion;
	}
	public void setTotalQuestion(int totalQuestion) {
		this.totalQuestion = totalQuestion;
	}
	public int getNumberOfSetsDone() {
		return numberOfSetsDone;
	}
	public void setNumberOfSetsDone(int numberOfSetsDone) {
		this.numberOfSetsDone = numberOfSetsDone;
	}
	public int getQuestionSetRunningValue() {
		return questionSetRunningValue;
	}
	public void setQuestionSetRunningValue(int questionSetRunningValue) {
		this.questionSetRunningValue = questionSetRunningValue;
	}
	public int getQuestionSetTotalValue() {
		return questionSetTotalValue;
	}
	public void setQuestionSetTotalValue(int questionSetTotalValue) {
		this.questionSetTotalValue = questionSetTotalValue;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Map<String, String> getSelection() {
		return selection;
	}
	public void setSelection(Map<String, String> selection) {
		this.selection = selection;
	}
	public String getMemberNumber() {
		return memberNumber;
	}
	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}
    
    
    
    
    
     
}