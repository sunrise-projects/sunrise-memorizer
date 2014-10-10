package com.sunriseprojects.memorizer.web.rest.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sunriseprojects.memorizer.web.rest.model.QuestionModel;
import com.sunriseprojects.memorizer.web.services.QuestionService;
 
/**
 * Handles requests for the Employee service.
 */
@Controller
public class QuestionRESTController {
    
	@Autowired
	QuestionService questionServiceProxy;
	
	@Value("${excel.location}")
	private String excelLocation;
	
    
	private static final Logger logger = LoggerFactory.getLogger(QuestionRESTController.class);

    @RequestMapping(value = QuestionRestURIConstants.START_QUESTION_FILE, method = RequestMethod.GET)
    public @ResponseBody QuestionModel startQuestionFile(@PathVariable("file") String file) {        
        return questionServiceProxy.doInitQuestion(file, excelLocation);
    }
    
    
    @RequestMapping(value = QuestionRestURIConstants.START_QUESTION, method = RequestMethod.GET)
    public @ResponseBody QuestionModel startQuestion() {
        return questionServiceProxy.doInitQuestion(null, excelLocation);
    }
     
    @RequestMapping(value = QuestionRestURIConstants.NEXT_QUESTION, method = RequestMethod.GET)
    public @ResponseBody QuestionModel nextQuestion(@PathVariable("sessionId") String sessionId) {        
        return questionServiceProxy.doNextQuestion(sessionId);
    }

    @RequestMapping(value = QuestionRestURIConstants.CHECK_ANSWER, method = RequestMethod.GET)
    public @ResponseBody QuestionModel checkAnswer(@PathVariable("sessionId") String sessionId,
    		@PathVariable("number") int number,
    		@PathVariable("answer") String answer) {        
        return questionServiceProxy.doCheckAnswer(sessionId, number, answer);
    }

    @RequestMapping(value = QuestionRestURIConstants.REVIEW_ANSWER, method = RequestMethod.GET)
    public @ResponseBody QuestionModel showAnswers(@PathVariable("sessionId") String sessionId) {
    	return questionServiceProxy.doShowAnswers(sessionId);
   }    
    
}