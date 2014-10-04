package com.sunriseprojects.memorizer.core.console;
import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.framework.SpringRuleEngine;

import junit.framework.TestCase;


public class CheckAnswerTest extends TestCase {

	public void testSuccessfulFlow() throws Exception {
		
		SpringRuleEngine engine = CheckAnswerRuleEngine.getEngine("CheckAnswerProcessor");
		ContextDAO application = new ContextDAO();
		
		application.setSessiondId("7bb8c9bc-f75b-46f3-920d-02be5050e185");
		application.setQuestionSessionNumber(1);
		application.setQuestionAnswer("c");
		
		engine.processRequest(application);
		
		System.out.println(application.getStatus().toString());
		
		
		assertEquals(ContextDAO.APPROVED, application.getStatus());
	}
	
	
	
	
	
}
