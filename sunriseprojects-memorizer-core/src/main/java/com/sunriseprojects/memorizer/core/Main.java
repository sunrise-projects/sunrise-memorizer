package com.sunriseprojects.memorizer.core;

import com.sunriseprojects.memorizer.core.framework.LoanApplication;
import com.sunriseprojects.memorizer.core.framework.LoanProcessRuleEngine;
import com.sunriseprojects.memorizer.core.framework.SpringRuleEngine;

public class Main {

	/*
	 1) 25 question
	 2) 25 % 4 = 1 -> 24 - 1 = 24 --> 24 / 4 = 6
	 3) select all from 25
 
	 */
	
	public static void main(String[] args) {
		SpringRuleEngine engine = LoanProcessRuleEngine.getEngine("SharkysExpressLoansApplicationProcessor");
		LoanApplication application = new LoanApplication();
		application.setFirstName("John");
		application.setLastName("Doe");
		application.setStateCode("TX");
		application.setExpences(4500);
		application.setIncome(7000);
		try {
			engine.processRequest(application);
			System.out.println(application.getStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//assertEquals(LoanApplication.APPROVED, application.getStatus());		
	}
	
	
}
