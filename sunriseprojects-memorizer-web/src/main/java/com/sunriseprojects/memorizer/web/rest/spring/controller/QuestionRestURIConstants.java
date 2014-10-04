package com.sunriseprojects.memorizer.web.rest.spring.controller;
 
public class QuestionRestURIConstants {
 
	public static final String START_QUESTION_FILE = "/rest/question/start/{file}";
    public static final String START_QUESTION = "/rest/question/start";
    public static final String NEXT_QUESTION = "/rest/question/next/{sessionId}";
    public static final String CHECK_ANSWER = "/rest/question/check/{sessionId}/{number}/{answer}";
    public static final String REVIEW_ANSWER = "/rest/question/review/{sessionId}";

}