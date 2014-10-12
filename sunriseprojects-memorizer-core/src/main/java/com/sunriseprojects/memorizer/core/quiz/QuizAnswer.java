package com.sunriseprojects.memorizer.core.quiz;

import java.io.Serializable;

public class QuizAnswer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 112313123L;

	private String questionAnswer;
	private String testerAnswer;
	private int questionNumber;
	private boolean correct;
	public String getQuestionAnswer() {
		return questionAnswer;
	}
	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}
	public String getTesterAnswer() {
		return testerAnswer;
	}
	public void setTesterAnswer(String testerAnswer) {
		this.testerAnswer = testerAnswer;
	}
	public int getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}
	public boolean isCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	@Override
	public String toString() {
		return "QuizAnswer [questionAnswer=" + questionAnswer
				+ ", testerAnswer=" + testerAnswer + ", questionNumber="
				+ questionNumber + ", correct=" + correct + "]";
	}
	
	
	
	
}
