package com.sunriseprojects.memorizer.core.data;

import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.SessionDAO;


public abstract class DataHandler {

	public enum Types {
	    EXCEL, SERIALIZED
	}
	
	public static DataHandler getInstance() {
		DataHandler data = new SerializedDataHandler();
		return data;
	}
	
	public static DataHandler getInstance(Types types) {
		DataHandler data = null;
		if(types.equals(Types.EXCEL)) {
			data = new ExcelDataHandler();
		} else {
			data = new SerializedDataHandler();
		}
		return data;
	}
	
	
	public abstract boolean saveSessionData(ContextDAO ctx);
	
	public abstract SessionDAO getSessionData(ContextDAO application) throws Exception;
	
	public abstract boolean saveData(ContextDAO ctx, SessionDAO dao)  throws Exception;
	
	public abstract Object loadData(Object obj);
	

	
}
