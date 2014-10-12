package com.sunriseprojects.memorizer.web.constants;
 
public class QuestionRestConstants {
 
	public static final String START_QUIZ_FILE = "/rest/quiz/start/{file}";
	public static final String QUIZ_ANSWER = "/rest/quiz/answer/{uniqueId}/{questionNumber}/{questionAnswer}";
	public static final String QUIZ_RESULTS = "/rest/quiz/results/{uniqueId}";
	
	public static final String START_QUESTION_FILE = "/rest/question/start/{file}";
    public static final String START_QUESTION = "/rest/question/start";
    public static final String NEXT_QUESTION = "/rest/question/next/{sessionId}";
    public static final String CHECK_ANSWER = "/rest/question/check/{sessionId}/{number}/{answer}";
    public static final String REVIEW_ANSWER = "/rest/question/review/{sessionId}";

}