package com.sunriseprojects.memorizer.core.quiz;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sunriseprojects.memorizer.core.dao.QuestionDAO;

// http://java.dzone.com/articles/design-patterns-abstract-factory

public class QuizTest {

	static Logger logger = Logger.getLogger(QuizTest.class);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void test() {
		AbstractQuizFactory abstractQuizFactory = new ExcelQuizFactory();
		Quiz quiz = abstractQuizFactory.createQuiz();
		quiz.setCode("1234");
		quiz.setDescription("sample desc");
		quiz.setStartDate(new Date());
		
		logger.info(quiz.getCode());
		logger.info(quiz.getDescription());
		logger.info(quiz.getStartDate());
		
		StringBuilder uniqueId = new StringBuilder();
		List<QuestionDAO> questionList = new ArrayList<QuestionDAO>();
		
		quiz.processInput(null, uniqueId, questionList);
		
		logger.info(uniqueId.toString());
		logger.info(questionList.toString());
		
		List<QuestionDAO> questionList2 = (List<QuestionDAO>)quiz.showQuestions(uniqueId.toString());
		logger.info(questionList2.toString());
		
		 quiz.saveAnswer(uniqueId.toString(), "1", "d");
		 quiz.saveAnswer(uniqueId.toString(), "9", "a");
		 quiz.saveAnswer(uniqueId.toString(), "6", "a");
		 
		 
		 List<QuestionDAO> questionListOut = new ArrayList<QuestionDAO>();
		 StringBuilder totalCount = new StringBuilder();
		 StringBuilder totalCorrect = new StringBuilder();
		 StringBuilder average = new StringBuilder();
		 quiz.computeResult(uniqueId.toString(), questionListOut, totalCount, totalCorrect, average);
		 logger.info(questionListOut.toString());
		 logger.info("totalCount:"+totalCount.toString());
		 logger.info("totalCorrect:"+totalCorrect.toString());
		 logger.info("average:"+average.toString());
		
		
		
		
		
		
		
		
	}

}
