package com.sunriseprojects.memorizer.core.console;

import java.util.List;

import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.data.DataHandler;

import junit.framework.TestCase;

public class LoadExcelDataTest extends TestCase {

	public void testSuccessfulFlow() throws Exception {
		
		
		System.out.println("Test");
		
		DataHandler handler = DataHandler.getInstance(DataHandler.Types.EXCEL);
		
		@SuppressWarnings("unchecked")
		List<QuestionDAO> questions = (List<QuestionDAO>)handler.loadData("C:\\1Book1.xlsx");
		System.out.println(questions.toString());

	}

}
