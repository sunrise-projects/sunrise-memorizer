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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
 















import com.sunriseprojects.memorizer.web.rest.spring.model.Employee;
import com.sunriseprojects.memorizer.web.rest.spring.model.QuestionModel;
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
     
	@Value("${excel.location}")
	private String excelLocation;
	
    private static final Logger logger = LoggerFactory.getLogger(QuestionRESTController.class);

    @RequestMapping(value = QuestionRestURIConstants.START_QUESTION_FILE, method = RequestMethod.GET)
    public @ResponseBody QuestionModel startQuestionFile(@PathVariable("file") String file) {
        
    	QuestionModel q = startQuestionCore(file) ;
        return q;
    }
    
    
    @RequestMapping(value = QuestionRestURIConstants.START_QUESTION, method = RequestMethod.GET)
    public @ResponseBody QuestionModel startQuestion() {
        
    	QuestionModel q = startQuestionCore(null) ;
        return q;
    }

    public QuestionModel startQuestionCore(String file) {
		SpringRuleEngine startQengine = StartQARuleEngine
				.getEngine("StartQAProcessor");
		
		ContextDAO application = new ContextDAO();
		
		if(file != null ) {
			
			byte[] valueDecoded= Base64.decodeBase64(file.getBytes() );
			String fname = new String(valueDecoded);
			
			application.setExcelFile(excelLocation+fname);
		}
		
		application.setMemberNumber("12345");
		
		try {
			startQengine.processRequest(application);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(application.getSessiondId());
		
		
		QuestionDAO qa = application.getQandaQuestion();
		
    	logger.info("Start QuestionModel");
    	QuestionModel q = new QuestionModel();
        q.setSessionId(application.getSessiondId());
        q.setQuestion(qa.getQuestion());
        q.setSelection(qa.getSelections());
        q.setMemberNumber("12345");
        q.setStatus(application.getStatus());
        q.setNumber(application.getQandaSessionDAO().getQuestionSessionNumber());
        q.setTotalQuestionRunningValue((application.getQandaSessionDAO().getTotalQuestionRunningValue()-1));
        q.setTotalQuestion(application.getQandaSessionDAO().getTotalQuestion());
        q.setNumberOfSetsDone(application.getQandaSessionDAO().getNumberOfSetsDone());
        q.setQuestionSetRunningValue(application.getQandaSessionDAO().getQuestionSetRunningValue()-1);
        q.setQuestionSetTotalValue(application.getQandaSessionDAO().getQuestionSetTotalValue());
                
        return q;
    }
    
    @RequestMapping(value = QuestionRestURIConstants.NEXT_QUESTION, method = RequestMethod.GET)
    public @ResponseBody QuestionModel nextQuestion(@PathVariable("sessionId") String sessionId) {
        
		SpringRuleEngine nextQengine = NextQuestionRuleEngine.getEngine("NextQuestionProcessor");
		
		ContextDAO applicationNext = new ContextDAO();
		applicationNext.setMemberNumber("12345");
		applicationNext.setSessiondId(sessionId);
		try {
			nextQengine.processRequest(applicationNext);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		QuestionDAO qa = applicationNext.getQandaQuestion();		
    	logger.info("Start QuestionModel");
    	QuestionModel q = new QuestionModel();
        q.setSessionId(applicationNext.getSessiondId());
        if(qa == null) {
            q.setQuestion("");
            q.setSelection(new HashMap<String, String>());        	
        } else {
            q.setQuestion(qa.getQuestion());
            q.setSelection(qa.getSelections());
        }
        q.setMemberNumber(applicationNext.getMemberNumber());
        q.setStatus(applicationNext.getStatus());
        q.setNumber(applicationNext.getQandaSessionDAO().getQuestionSessionNumber());
        
        q.setTotalQuestionRunningValue((applicationNext.getQandaSessionDAO().getTotalQuestionRunningValue()-1));
        q.setTotalQuestion(applicationNext.getQandaSessionDAO().getTotalQuestion());
        q.setNumberOfSetsDone(applicationNext.getQandaSessionDAO().getNumberOfSetsDone());
        q.setQuestionSetRunningValue(applicationNext.getQandaSessionDAO().getQuestionSetRunningValue()-1);
        q.setQuestionSetTotalValue(applicationNext.getQandaSessionDAO().getQuestionSetTotalValue());
        
        
        return q;
    }

    @RequestMapping(value = QuestionRestURIConstants.CHECK_ANSWER, method = RequestMethod.GET)
    public @ResponseBody QuestionModel checkAnswer(@PathVariable("sessionId") String sessionId,
    		@PathVariable("number") int number,
    		@PathVariable("answer") String answer) {
        
    	SpringRuleEngine checkAengine = CheckAnswerRuleEngine.getEngine("CheckAnswerProcessor");
		ContextDAO application = new ContextDAO();		
		application.setMemberNumber("12345");
		application.setSessiondId(sessionId);
		application.setQuestionSessionNumber(number);
		application.setQuestionAnswer(answer);	
		
		try {
			checkAengine.processRequest(application);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						
    	logger.info("Start QuestionModel");
    	QuestionModel q = new QuestionModel();
        q.setSessionId(application.getSessiondId());
        q.setStatus(application.getStatus());
        return q;
    }

    @RequestMapping(value = QuestionRestURIConstants.REVIEW_ANSWER, method = RequestMethod.GET)
    public @ResponseBody QuestionModel showAnswers(@PathVariable("sessionId") String sessionId) {
        
    	SpringRuleEngine wrongEngine = WrongAnswerRuleEngine.getEngine("WrongAnswerProcessor");
		
		ContextDAO applicationNext = new ContextDAO();
		applicationNext.setMemberNumber("12345");
		applicationNext.setSessiondId(sessionId);
		try {
			wrongEngine.processRequest(applicationNext);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		QuestionDAO qa = applicationNext.getQandaQuestion();		
    	logger.info("Start QuestionModel");
    	QuestionModel q = new QuestionModel();
        q.setSessionId(applicationNext.getSessiondId());
        if(qa == null) {
            q.setQuestion("");
            q.setSelection(new HashMap<String, String>());   
            q.setAnswer("");
            q.setExplanation("");
        } else {
            q.setQuestion(qa.getQuestion());
            q.setSelection(qa.getSelections());
            q.setAnswer(qa.getAnswer());
            q.setExplanation(qa.getExplanation());
        }
        q.setMemberNumber(applicationNext.getMemberNumber());
        q.setStatus(applicationNext.getStatus());
        q.setNumber(applicationNext.getQandaSessionDAO().getQuestionSessionNumber());
        
        q.setTotalQuestionRunningValue((applicationNext.getQandaSessionDAO().getTotalQuestionRunningValue()-1));
        q.setTotalQuestion(applicationNext.getQandaSessionDAO().getTotalQuestion());
        q.setNumberOfSetsDone(applicationNext.getQandaSessionDAO().getNumberOfSetsDone());
        q.setQuestionSetRunningValue(applicationNext.getQandaSessionDAO().getQuestionSetRunningValue()-1);
        q.setQuestionSetTotalValue(applicationNext.getQandaSessionDAO().getQuestionSetTotalValue());
        
        
        return q;
    }    
    
}