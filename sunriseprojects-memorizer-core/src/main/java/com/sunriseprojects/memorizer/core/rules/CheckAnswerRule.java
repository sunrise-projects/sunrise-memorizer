package com.sunriseprojects.memorizer.core.rules;

import java.util.LinkedList;
import java.util.List;

import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.dao.SessionDAO;
import com.sunriseprojects.memorizer.core.services.QAPersistenceAwareRule;

public class CheckAnswerRule extends QAPersistenceAwareRule {

	protected boolean makeDecision(Object arg) throws Exception {
		
		ContextDAO application = (ContextDAO) arg;
		
		if(application.getMemberNumber() == null) {
			application.setStatus(ContextDAO.INSUFFICIENT_DATA);
			return false;
		}
		
		return true;

	}

}