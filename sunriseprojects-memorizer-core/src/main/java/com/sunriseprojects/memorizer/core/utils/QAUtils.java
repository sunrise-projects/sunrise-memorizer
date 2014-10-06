package com.sunriseprojects.memorizer.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.dao.SessionDAO;

public class QAUtils {

	public static List<String>  shuffle(Map<String,String> map) {
		Map<String,String> out = new HashMap<String,String>();
		List<String> keys = new ArrayList<String>(map.keySet());
		Collections.shuffle(keys);	
		return keys;
		
	}
	
	public static void evaluteQuestionSet(SessionDAO session, List<QuestionDAO> questions, ContextDAO application ) {
		List<Integer> questionIndexes = new ArrayList<Integer>();
		for ( int i=0; i<questions.size(); i++ ) {
			QuestionDAO  qandaQuestion = questions.get(i); 
			//question times to be asked if answered correctly
			if(qandaQuestion.getCorrectAnswerCount() < 2) questionIndexes.add(i);
		}
		
		if(questionIndexes.isEmpty()) {
			application.setStatus(ContextDAO.NO_QUESTION_REMAINING);
		} else {
			application.setStatus(ContextDAO.QUESTION_AVAILABLE);
			Collections.shuffle(questionIndexes);
			
			LinkedList<Integer> questionIndexesLimited = new LinkedList<Integer>();
			//this happens if question indexes is greater than question set 
			if ( questionIndexes.size() < session.getQuestionSetTotalValue() ) {
				//1 to 7
				//questionIndexesLimited = questionIndexes.subList(0, questionIndexes.size());
				for (int i=0;i<questionIndexes.size();i++) {
					questionIndexesLimited.add(questionIndexes.get(i));
				}
				
				
			} else {
				//get question set using the cutoff. i.e. 8
				//questionIndexesLimited = questionIndexes.subList(0, session.getQuestionSetTotalValue());
				for (int i=0;i<session.getQuestionSetTotalValue();i++) {
					questionIndexesLimited.add(questionIndexes.get(i));
				}
			}
			

			session.setQuestionSet(questionIndexesLimited);
			
			int index = session.getQuestionSetRunningValue()-1; //because this is zero based.
			application.setQandaQuestion(session.getQuestions().get(questionIndexesLimited.get(index)));
			session.setQuestionSessionNumber(session.getQuestions().get(questionIndexesLimited.get(index)).getQuestionNumber());
			session.setTotalQuestionRunningValue(session.getTotalQuestionRunningValue()+1);
			//session.setQuestionSetRunningValue(session.getQuestionSetRunningValue()+1);

		
		}
		
		
	}
	
}
