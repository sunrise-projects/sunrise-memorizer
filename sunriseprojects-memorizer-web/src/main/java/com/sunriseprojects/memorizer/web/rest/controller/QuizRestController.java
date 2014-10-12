package com.sunriseprojects.memorizer.web.rest.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.quiz.AbstractQuizFactory;
import com.sunriseprojects.memorizer.core.quiz.ExcelQuizFactory;
import com.sunriseprojects.memorizer.core.quiz.Quiz;
import com.sunriseprojects.memorizer.web.constants.QuestionRestConstants;
import com.sunriseprojects.memorizer.web.rest.model.QuestionModel;
import com.sunriseprojects.memorizer.web.rest.model.QuizModel;
import com.sunriseprojects.memorizer.web.rest.model.StatusModel;
import com.sunriseprojects.memorizer.web.services.QuestionService;
 
/**
 * Handles requests for the Employee service.
 */
@Controller
public class QuizRestController {
    
	@Autowired
	QuestionService questionServiceProxy;
	
	@Value("${excel.location}")
	private String excelLocation;
	
    
	static Logger logger = Logger.getLogger(QuizRestController.class);

    @RequestMapping(value = QuestionRestConstants.QUIZ_RESULTS, method = RequestMethod.GET)
    public @ResponseBody QuizModel quizResults(@PathVariable("uniqueId") String uniqueId) {        
        
		AbstractQuizFactory abstractQuizFactory = new ExcelQuizFactory();
		Quiz quiz = abstractQuizFactory.createQuiz();

		 List<QuestionDAO> questionListOut = new ArrayList<QuestionDAO>();
		 StringBuilder totalCount = new StringBuilder();
		 StringBuilder totalCorrect = new StringBuilder();
		 StringBuilder average = new StringBuilder();
		 quiz.computeResult(uniqueId.toString(), questionListOut, totalCount, totalCorrect, average);
		 logger.info(questionListOut.toString());
		 logger.info("totalCount:"+totalCount.toString());
		 logger.info("totalCorrect:"+totalCorrect.toString());
		 logger.info("average:"+average.toString());
		 
		QuizModel model = new QuizModel();
		model.setUniqueId(uniqueId.toString());
		model.setQuestionList(questionListOut);
		model.setTotalCorrect(totalCorrect.toString());
		model.setTotalCount(totalCount.toString());
		model.setAverage(average.toString());
		
    	return model;
    }
    	
	
    @RequestMapping(value = QuestionRestConstants.QUIZ_ANSWER, method = RequestMethod.GET)
    public @ResponseBody StatusModel checkAnswer(@PathVariable("uniqueId") String uniqueId,
    		@PathVariable("questionNumber") String questionNumber,
    		@PathVariable("questionAnswer") String questionAnswer) {        
    	AbstractQuizFactory abstractQuizFactory = new ExcelQuizFactory();
		Quiz quiz = abstractQuizFactory.createQuiz();
		quiz.saveAnswer(uniqueId, questionNumber, questionAnswer);
		StatusModel s = new StatusModel();
		s.setStatusText("Success");
		s.setStatusCode(0);
        return s;
    }
	

    @RequestMapping(value = QuestionRestConstants.START_QUIZ_FILE, method = RequestMethod.GET)
    public @ResponseBody QuizModel startQuizFile(@PathVariable("file") String file) {        
        
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
		
		byte[] valueDecoded= Base64.decodeBase64(file.getBytes() );
		String fname = new String(valueDecoded);
		String filePath = excelLocation+"/"+fname;
		
		quiz.processInput(filePath, uniqueId, questionList);

		QuizModel model = new QuizModel();
		model.setUniqueId(uniqueId.toString());
		model.setQuestionList(questionList);
		
    	return model;
    }
       
}