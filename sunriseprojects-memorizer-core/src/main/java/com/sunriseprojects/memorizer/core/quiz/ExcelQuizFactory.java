package com.sunriseprojects.memorizer.core.quiz;

public class ExcelQuizFactory implements AbstractQuizFactory {

	@Override
	public Quiz createQuiz() {
		Quiz quiz = new ExcelQuiz();
		return quiz;		
	}

}
