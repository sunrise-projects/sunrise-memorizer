package com.sunriseprojects.memorizer.core.console;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import com.sunriseprojects.memorizer.core.actions.StartQAPositiveAction;
import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.framework.SpringRuleEngine;
import com.sunriseprojects.memorizer.core.utils.QAUtils;

import junit.framework.TestCase;

public class RunAllTest extends TestCase {

	private static Scanner scanIn = null;
	
	@SuppressWarnings("resource")
	public void testSuccessfulFlow() throws Exception {
	
		SpringRuleEngine startQengine = StartQARuleEngine
				.getEngine("StartQAProcessor");
		
		SpringRuleEngine nextQengine = NextQuestionRuleEngine.getEngine("NextQuestionProcessor");
		
		SpringRuleEngine checkAengine = CheckAnswerRuleEngine.getEngine("CheckAnswerProcessor");
		
		SpringRuleEngine wrongEngine = WrongAnswerRuleEngine.getEngine("WrongAnswerProcessor");
				
		ContextDAO application = new ContextDAO();
		application.setMemberNumber("12345");
		startQengine.processRequest(application);
		
		int counter = 1;		
		System.out.println("--------------");
		System.out.println("Question #"+counter);
		String answer = processQuestion(application);
		checkAnswer(checkAengine, application, answer); 
		System.out.println(application.getStatus());
		System.out.println("--------------");		

		boolean moreQuestion = true;
		while (moreQuestion == true) {

			//next question
			ContextDAO applicationNext = new ContextDAO();
			applicationNext.setMemberNumber("12345");
			applicationNext.setSessiondId(application.getSessiondId());
			nextQengine.processRequest(applicationNext);
			
			if (applicationNext.getStatus().equals(ContextDAO.QUESTION_AVAILABLE)) {
				counter++;
				System.out.println("Question #"+counter);
				answer = processQuestion(applicationNext);
				checkAnswer(checkAengine, applicationNext, answer); 
				System.out.println(applicationNext.getStatus());
				System.out.println("--------------");
			} else if (applicationNext.getStatus().equals(ContextDAO.QUESTION_SET_TOTAL_REACHED)) {
				//
				//call showanswer and initialize the shuffled question, 
				//then call process questin and check answer again.
				//continue while end not reached yet. 1-3, if 3 then end reached.
				//ShowAnswer
				//REMAINING_WRONG_ANSWERS_EXIST
				boolean moreWrongAnswer = true;
				System.out.println("+++++++++++++++++++++++++++++ANSWERS START");
				while (moreWrongAnswer == true) {
					ContextDAO applicationNextWrongAnswer = new ContextDAO();
					applicationNextWrongAnswer.setMemberNumber("12345");
					applicationNextWrongAnswer.setSessiondId(application.getSessiondId());
					wrongEngine.processRequest(applicationNextWrongAnswer);					
					if(applicationNextWrongAnswer.getStatus().equals(ContextDAO.WRONG_ANSWER_FOUND)) {
						processWrongAnswer(applicationNextWrongAnswer);
					} else {
						moreWrongAnswer = false;
					}
					System.out.println(applicationNextWrongAnswer.getStatus());
					System.out.println("--------------");
				}
				System.out.println("+++++++++++++++++++++++++++++ANSWERS END");
				System.out.println(applicationNext.getStatus());
				
			} else {
				moreQuestion = false;
				System.out.println(applicationNext.getStatus());
			}
			
		}	
		
		scanIn.close();
	
	}

	public static String processWrongAnswer(ContextDAO application) {
		
		QuestionDAO qa = application.getQandaQuestion();
		System.out.print("Question Code#: "+application.getQandaSessionDAO()
				.getQuestionSessionNumber());	
		System.out.print(" getTotalQuestionRunningValue: "+(application.getQandaSessionDAO().getTotalQuestionRunningValue()-1));
		System.out.print(" getTotalQuestion: "+application.getQandaSessionDAO().getTotalQuestion());
		System.out.print(" getNumberOfSetsDone: "+application.getQandaSessionDAO().getNumberOfSetsDone());
		System.out.print(" getQuestionSetRunningValue: "+(application.getQandaSessionDAO().getQuestionSetRunningValue()));
		System.out.println(" getQuestionSetTotalValue: "+application.getQandaSessionDAO().getQuestionSetTotalValue());
		
		System.out.println("Question: "+qa.getQuestion());
		Map<String, String> m1 = new TreeMap<String, String>(qa.getSelections());
		
		Iterator<Entry<String, String>> i1 = m1.entrySet().iterator();
		while(i1.hasNext()) {
			Entry<String, String> e1 = i1.next();
			System.out.println(e1.getKey()+"="+e1.getValue());			
		}
		System.out.println("Answer: "+qa.getAnswer());
		System.out.println("Explanation: "+ qa.getExplanation()==null ? "NA":qa.getExplanation());
		
//		System.out.print("Continue ? : ");
//		scanIn = new Scanner(System.in);
//		scanIn.nextLine();				
		return null;
	}

	
	public static String processQuestion(ContextDAO application) {
		
		QuestionDAO qa = application.getQandaQuestion();
		System.out.print("Code#: "+application.getQandaSessionDAO()
				.getQuestionSessionNumber());	
		System.out.print(" getTotalQuestionRunningValue: "+(application.getQandaSessionDAO().getTotalQuestionRunningValue()-1));
		System.out.print(" getTotalQuestion: "+application.getQandaSessionDAO().getTotalQuestion());
		System.out.print(" getNumberOfSetsDone: "+application.getQandaSessionDAO().getNumberOfSetsDone());
		System.out.print(" getQuestionSetRunningValue: "+(application.getQandaSessionDAO().getQuestionSetRunningValue()-1));
		System.out.println(" getQuestionSetTotalValue: "+application.getQandaSessionDAO().getQuestionSetTotalValue());
		
		System.out.println("Question: "+qa.getQuestion());
		System.out.println("Select answer below:");
		Map<String, String> m1 = qa.getSelections();
		List<String> keys = QAUtils.shuffle(m1);
		
		Map<Integer, String> mapAnswer = new HashMap<Integer, String>();
		int mapAnswerCount = 1;
		for (Object o : keys) {
			mapAnswer.put(mapAnswerCount, o.toString());
			System.out.println(mapAnswerCount + "=" + m1.get(o));
			mapAnswerCount++;
		}
		System.out.print("Answer ? : ");
		scanIn = new Scanner(System.in);
		String answerInt = scanIn.nextLine();
		
		String answer = mapAnswer.get(Integer.valueOf(answerInt));

		System.out.println("Your answer is: "+answerInt + " = " +answer);
		
		return answer;
	}
	
	public static void checkAnswer(SpringRuleEngine engine, ContextDAO applicationIn, String answer) {
		String sessionId =   applicationIn.getSessiondId();
		Integer questionNumber =  applicationIn.getQandaSessionDAO()
				.getQuestionSessionNumber();
		ContextDAO application = new ContextDAO();		
		application.setMemberNumber("12345");
		application.setSessiondId(sessionId);
		application.setQuestionSessionNumber(questionNumber);
		application.setQuestionAnswer(answer);		
		try {
			engine.processRequest(application);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Checking answer... : "+application.getStatus().toString());
				
	}

}
