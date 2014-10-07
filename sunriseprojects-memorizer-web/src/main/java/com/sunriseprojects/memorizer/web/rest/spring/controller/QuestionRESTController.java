package com.sunriseprojects.memorizer.web.rest.spring.controller;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
 





















import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
 


















import com.sunriseprojects.memorizer.web.rest.spring.model.Employee;
import com.sunriseprojects.memorizer.web.rest.spring.model.QuestionModel;
import com.sunriseprojects.memorizer.web.services.QuestionService;
import com.sunriseprojects.memorizer.core.console.CheckAnswerRuleEngine;
import com.sunriseprojects.memorizer.core.console.NextQuestionRuleEngine;
import com.sunriseprojects.memorizer.core.console.StartQARuleEngine;
import com.sunriseprojects.memorizer.core.console.WrongAnswerRuleEngine;
import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.framework.SpringRuleEngine;
 
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