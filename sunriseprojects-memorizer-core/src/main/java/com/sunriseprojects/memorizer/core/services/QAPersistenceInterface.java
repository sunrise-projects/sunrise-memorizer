package com.sunriseprojects.memorizer.core.services;

import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.SessionDAO;


public interface QAPersistenceInterface {
	public void recordApproval(ContextDAO application) throws Exception;
	public void recordRejection(ContextDAO application) throws Exception;
	public void recordIncomplete(ContextDAO application) throws Exception;
	
	public void saveSessionData(ContextDAO application) throws Exception;
	
	public SessionDAO getSessionData(ContextDAO application) throws Exception;
	
	public boolean saveData(ContextDAO ctx, SessionDAO dao)  throws Exception;
	
}
