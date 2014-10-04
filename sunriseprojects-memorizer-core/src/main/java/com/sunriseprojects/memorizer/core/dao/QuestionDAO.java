package com.sunriseprojects.memorizer.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class QuestionDAO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int questionNumber;
	private String question;
	private String answer;
	private int correctAnswerCount;
	private String explanation;
	private String illustration;
	private Map<String,String> selections;
	
	
	public Map<String, String> getSelections() {
		return selections;
	}

	public void setSelections(Map<String, String> selections) {
		this.selections = selections;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}
	
	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getCorrectAnswerCount() {
		return correctAnswerCount;
	}
	public void setCorrectAnswerCount(int correctAnswerCount) {
		this.correctAnswerCount = correctAnswerCount;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public String getIllustration() {
		return illustration;
	}
	public void setIllustration(String illustration) {
		this.illustration = illustration;
	}

	@Override
	public String toString() {
		return "QAQuestionDAO [questionNumber=" + questionNumber
				+ ", question=" + question + ", answer=" + answer
				+ ", correctAnswerCount=" + correctAnswerCount
				+ ", explanation=" + explanation + ", illustration="
				+ illustration + ", selections=" + selections + "]";
	}
	
	
	
	
	
}
