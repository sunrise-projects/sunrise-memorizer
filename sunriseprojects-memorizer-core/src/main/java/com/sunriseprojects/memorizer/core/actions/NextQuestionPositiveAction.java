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

import org.apache.log4j.Logger;

import com.sunriseprojects.memorizer.core.constants.DAOConstants;
import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.dao.SessionDAO;
import com.sunriseprojects.memorizer.core.services.QAPersistenceAwareAction;
import com.sunriseprojects.memorizer.core.utils.QAUtils;


public class NextQuestionPositiveAction extends QAPersistenceAwareAction {

	static Logger logger = Logger.getLogger(NextQuestionPositiveAction.class);

	protected void doExecute(Object arg) throws Exception {
		
		ContextDAO application = (ContextDAO)arg;
		SessionDAO session = this.getPersistenceService()
				.getSessionData(application);
		
		if(session.getStatus() != null && session.getStatus().equals(DAOConstants.NO_MORE_WRONG_ANSWERS_TO_REVIEW)) {
			//re-init wrong answer variables
			session.setWrongAnswers(null);
			session.setWrongAnswersRunningValue(0);
			
			//will do this in showAnsers logic. to initialize it there.
			//goto next question set
			//initialize to 1 again
			session.setQuestionSetRunningValue(1);
			session.setNumberOfSetsDone(session.getNumberOfSetsDone()+1); //increment sets done.
			QAUtils.evaluteQuestionSet(session, session.getQuestions(), application );
			//initialize this to null so that it will go back to normal routine
			session.setStatus(null);
			
		} else {
			logger.debug("getWrongAnswersIndexes: "+session.getWrongAnswers());
			logger.debug("getWrongAnswersRunningValue: "+session.getWrongAnswersRunningValue());
			logger.debug("getQuestionSetRunningValue: "+session.getQuestionSetRunningValue());
			logger.debug("getQuestionSetTotalValue: "+session.getQuestionSetTotalValue());
		
			List<Integer> questionIndexesLimited = session.getQuestionSet();
			int questionSetRunningValue = session.getQuestionSetRunningValue();
			int questionSetTotalValue = session.getQuestionSetTotalValue();
			application.setStatus(ContextDAO.QUESTION_AVAILABLE);
			// when running value reached total set count, start again
			if ( questionSetRunningValue < questionSetTotalValue ) {
				// 0 based
				int index = session.getQuestionSetRunningValue(); 
				
				//if(questionIndexesLimited.size()==1) index=0;
				logger.debug("questionIndexesLimited=index: "+questionIndexesLimited.toString()+"="+index);
				
				//if(questionIndexesLimited.size()!=1) {
				//if(session.getTotalQuestionRunningValue() <= session.getTotalQuestion()) {
				try {
					application.setQandaQuestion(session.getQuestions().get(questionIndexesLimited.get(index)));
					session.setQuestionSessionNumber(session.getQuestions().get(questionIndexesLimited.get(index)).getQuestionNumber());
					session.setTotalQuestionRunningValue(session.getTotalQuestionRunningValue()+1);
					session.setQuestionSetRunningValue(session.getQuestionSetRunningValue()+1);
				//} else {
				//	application.setStatus(ContextDAO.NO_QUESTION_REMAINING);
				//}
				} catch (IndexOutOfBoundsException e) {
					logger.debug("Exception caught: "+e.toString());
					//application.setStatus(ContextDAO.NO_QUESTION_REMAINING);
					application.setStatus(ContextDAO.QUESTION_SET_TOTAL_REACHED);
				}
				
			} else {
				
				//if question set size is equal to wrong answers size then advise to see the answers			
				application.setStatus(ContextDAO.QUESTION_SET_TOTAL_REACHED);	
			
			}
			
		}

		application.setQandaSessionDAO(session);
		this.getPersistenceService().saveSessionData(application);



	}

}
