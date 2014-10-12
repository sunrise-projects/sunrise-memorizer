package com.sunriseprojects.memorizer.web.constants;
 
public class QuestionMVCConstants {

	public static final String QUIZ_UPLOAD = "/quiz/upload";
    public static final String QUIZ_WELCOME = "/quiz/welcome";
    public static final String QUIZ_START = "/quiz/start/{file}";
    public static final String QUIZ_SHOW = "/quiz/show/{uniqueId}";
    public static final String QUIZ_ANSWER = "/quiz/answer/{uniqueId}/{questionNumber}/{questionAnswer}";
    public static final String QUIZ_RESULTS = "/quiz/results/{uniqueId}";
    
	public static final String QUESTION_UPLOAD = "/question/upload";
    public static final String WELCOME_QUESTION = "/question/welcome";
    public static final String START_QUESTION = "/question/start";
    public static final String NEXT_QUESTION = "/question/next/{sessionId}";
    public static final String REVIEW_ANSWER = "/question/review/{sessionId}";
    public static final String START_QUESTION_FILE = "/question/start/{file}";
    
    

}