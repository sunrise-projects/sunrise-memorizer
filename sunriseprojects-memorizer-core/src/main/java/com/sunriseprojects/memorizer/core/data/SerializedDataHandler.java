package com.sunriseprojects.memorizer.core.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.dao.SessionDAO;

public class SerializedDataHandler extends DataHandler {

	public boolean saveData(ContextDAO ctx, SessionDAO dao) {
		boolean ret = true;
		FileOutputStream fout=null;
		try {
			fout = new FileOutputStream("C:\\memorizer-"+ctx.getSessiondId()+".tmp");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(dao);
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = false;
		} finally {
			try {
				fout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ret = false;
			}
		}
		return ret;
	}
	
	public boolean saveSessionData(ContextDAO ctx) {
		SessionDAO dao = ctx.getQandaSessionDAO();
		
		saveData(ctx, dao);

		return true;
	}


	public SessionDAO getSessionData(ContextDAO ctx) throws Exception {
		
		FileInputStream fin = new FileInputStream("C:\\memorizer-"+ctx.getSessiondId()+".tmp");
		ObjectInputStream ois = new ObjectInputStream(fin);
		SessionDAO qASessionDAO = (SessionDAO) ois.readObject();
		
		return qASessionDAO;
		
	}

	@Override
	public Object loadData(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
