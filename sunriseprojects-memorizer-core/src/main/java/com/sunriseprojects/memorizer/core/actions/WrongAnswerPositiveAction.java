package com.sunriseprojects.memorizer.core.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.sunriseprojects.memorizer.core.constants.DAOConstants;
import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.dao.SessionDAO;
import com.sunriseprojects.memorizer.core.services.QAPersistenceAwareAction;
import com.sunriseprojects.memorizer.core.utils.QAUtils;


public class WrongAnswerPositiveAction extends QAPersistenceAwareAction {


	protected void doExecute(Object arg) throws Exception {
		
		ContextDAO application = (ContextDAO)arg;
		
		SessionDAO session = this.getPersistenceService()
				.getSessionData(application);
			
			//check if question set is done. else increment
			List<Integer> getWrongAnswersIndexes = session.getWrongAnswers();
			
			System.out.println("getWrongAnswersIndexes: "+getWrongAnswersIndexes);
			System.out.println("getWrongAnswersRunningValue: "+session.getWrongAnswersRunningValue());

			if ( session.getWrongAnswers() != null && session.getWrongAnswersRunningValue() < session.getWrongAnswers().size() ) { 
				int index = session.getWrongAnswersRunningValue(); // 0 based, default is 0				
				//wrong answer set is zero-based, q#10 is actually 9 in wrong answer question set
				int indexValue = getWrongAnswersIndexes.get(index);
				System.out.println("indexValue: "+indexValue);
				
				QuestionDAO dao = null;
				for (QuestionDAO qAQuestionDAO : session.getQuestions()) {
					if (qAQuestionDAO.getQuestionNumber() == indexValue ) {
						dao = qAQuestionDAO;
						break;
					}
				}
				//application.setQandaQuestion(session.getQuestions().get(indexValue));
				//session.setQuestionSessionNumber(session.getQuestions().get(indexValue).getQuestionNumber());
				System.out.println("wrong question: "+dao.toString());
				application.setQandaQuestion(dao);
				session.setQuestionSessionNumber(dao.getQuestionNumber());
				
				session.setWrongAnswersRunningValue(session.getWrongAnswersRunningValue()+1);//zero-based				
				application.setStatus(ContextDAO.WRONG_ANSWER_FOUND);
				//required to set to 1
				session.setQuestionSetRunningValue(1);
			} else {			
				application.setStatus(DAOConstants.NO_MORE_WRONG_ANSWERS_TO_REVIEW);
				session.setStatus(DAOConstants.NO_MORE_WRONG_ANSWERS_TO_REVIEW);
				
			}
		
		application.setQandaSessionDAO(session);
		this.getPersistenceService().saveSessionData(application);



	}

}
