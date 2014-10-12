package com.sunriseprojects.memorizer.core.quiz;

import java.util.List;

public interface Quiz {

	public void setCode(Object code);
	public void setDescription(Object description);
	public void setStartDate(Object startDate);
	public Object getCode();
	public Object getDescription();
	public Object getStartDate();
	
	public void processInput(Object inputFilename, Object uniqueIdOut, Object questionListOut); //uniqueId
	public Object showQuestions(Object uniqueId);
	public void saveAnswer(Object uniqueId, Object questionNumber, Object answerValue);
	public void computeResult(Object uniqueId, Object questionListOut, Object totalCount, Object totalCorrect, Object average);
	
}
