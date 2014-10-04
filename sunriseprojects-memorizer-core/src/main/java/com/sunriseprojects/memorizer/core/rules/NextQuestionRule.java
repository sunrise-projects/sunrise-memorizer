package com.sunriseprojects.memorizer.core.rules;


import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.services.QAPersistenceAwareRule;


public class NextQuestionRule extends QAPersistenceAwareRule {

	protected boolean makeDecision(Object arg) throws Exception {
		ContextDAO application = (ContextDAO) arg;
		
		if(application.getMemberNumber() == null) {
			application.setStatus(ContextDAO.INSUFFICIENT_DATA);
			return false;
		}
		
		
		
		
		return true;						
	}

}
