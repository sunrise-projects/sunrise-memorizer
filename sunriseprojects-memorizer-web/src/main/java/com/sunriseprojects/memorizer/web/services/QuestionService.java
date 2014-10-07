package com.sunriseprojects.memorizer.web.services;

import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunriseprojects.memorizer.core.console.CheckAnswerRuleEngine;
import com.sunriseprojects.memorizer.core.console.NextQuestionRuleEngine;
import com.sunriseprojects.memorizer.core.console.StartQARuleEngine;
import com.sunriseprojects.memorizer.core.console.WrongAnswerRuleEngine;
import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.framework.SpringRuleEngine;
import com.sunriseprojects.memorizer.web.rest.spring.model.QuestionModel;

public class QuestionService {

	private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
	
	SpringRuleEngine checkAengine = CheckAnswerRuleEngine.getEngine("CheckAnswerProcessor");
	SpringRuleEngine wrongEngine = WrongAnswerRuleEngine.getEngine("WrongAnswerProcessor");
	SpringRuleEngine nextQengine = NextQuestionRuleEngine.getEngine("NextQuestionProcessor");
	SpringRuleEngine startQengine = StartQARuleEngine.getEngine("StartQAProcessor");

	
	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void doInit() {
		logger.info("initializing...");
	}
	
	public QuestionModel doShowAnswers(String sessionId) {

		
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
	
	public QuestionModel doCheckAnswer(String sessionId, int number, String answer) {

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
	
	public QuestionModel doNextQuestion(String sessionId) {

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
	
	public QuestionModel doInitQuestion(String file, String excelLocation) {
		QuestionModel q = startQuestionCore(file, excelLocation) ;
        return q;
	}
	
    private QuestionModel startQuestionCore(String file, String excelLocation) {
	
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
	
	
}
