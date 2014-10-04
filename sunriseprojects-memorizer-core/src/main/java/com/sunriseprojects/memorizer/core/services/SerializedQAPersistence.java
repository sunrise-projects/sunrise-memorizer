package com.sunriseprojects.memorizer.core.services;

import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.SessionDAO;
import com.sunriseprojects.memorizer.core.data.DataHandler;


public class SerializedQAPersistence implements QAPersistenceInterface {

	public void recordApproval(ContextDAO application) throws Exception {
	}

	public void recordRejection(ContextDAO application) throws Exception {
	}

	public void recordIncomplete(ContextDAO application) throws Exception {
	}

	@Override
	public void saveSessionData(ContextDAO application) throws Exception {
		DataHandler handler = DataHandler.getInstance();
		handler.saveSessionData(application);
		
	}

	@Override
	public SessionDAO getSessionData(ContextDAO application) throws Exception {
		DataHandler handler = DataHandler.getInstance();
		SessionDAO qASessionDAO = handler.getSessionData(application);
		return qASessionDAO;
		
	}

	@Override
	public boolean saveData(ContextDAO ctx, SessionDAO dao)
			throws Exception {
		DataHandler handler = DataHandler.getInstance();
		return handler.saveData(ctx, dao);
	}

}
