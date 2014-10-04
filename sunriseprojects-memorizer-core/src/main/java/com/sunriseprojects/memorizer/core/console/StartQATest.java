package com.sunriseprojects.memorizer.core.console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sunriseprojects.memorizer.core.actions.StartQAPositiveAction;
import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.framework.SpringRuleEngine;
import com.sunriseprojects.memorizer.core.utils.QAUtils;

import junit.framework.TestCase;


public class StartQATest extends TestCase {

	public void twestShuffle() throws Exception {
		
		Map<String,String> m1 = new HashMap<String,String>();
		m1.put("a","a thing");
		m1.put("b","a fruit");
		m1.put("c","a vegetable");
		System.out.println(m1.toString());
		List<String> keys = QAUtils.shuffle(m1);
		
		for (Object o : keys) {
			System.out.println(o+"="+m1.get(o));
		}	
		
		
		
	}
	
	
	public void testSuccessfulFlow() throws Exception {
		
		SpringRuleEngine engine = StartQARuleEngine.getEngine("StartQAProcessor");
		ContextDAO application = new ContextDAO();
		engine.processRequest(application);
		
		QuestionDAO qa = application.getQandaQuestion();
		System.out.println(application.getQandaSessionDAO().getQuestionSessionNumber());
		System.out.println(qa.getQuestion());
		//System.out.println(qa.getAnswer());
		//System.out.println(qa.getSelections().toString());
		
		Map<String,String> m1 = qa.getSelections();
		List<String> keys = QAUtils.shuffle(m1);
		for (Object o : keys) {
			System.out.println(o+"="+m1.get(o));
		}			
		System.out.println(application.getSessiondId());
		
		
		
		assertEquals(ContextDAO.APPROVED, application.getStatus());
	}
	
	
	
	
	
}
