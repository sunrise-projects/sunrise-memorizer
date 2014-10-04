package com.sunriseprojects.memorizer.core.actions;

import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.services.QAPersistenceAwareAction;


public class CheckAnswerNegativeAction extends QAPersistenceAwareAction {

	protected void doExecute(Object arg) throws Exception {
		ContextDAO application = (ContextDAO) arg;

	}

}
