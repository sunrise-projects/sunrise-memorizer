package com.sunriseprojects.memorizer.core.actions;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.dao.SessionDAO;
import com.sunriseprojects.memorizer.core.data.DataHandler;
import com.sunriseprojects.memorizer.core.services.QAPersistenceAwareAction;
import com.sunriseprojects.memorizer.core.utils.QAUtils;


public class StartQAPositiveAction extends QAPersistenceAwareAction {

	static Logger logger = Logger.getLogger(StartQAPositiveAction.class);
	
	protected void doExecute(Object arg) throws Exception {
		
		ContextDAO application = (ContextDAO)arg;
		application.setStatus(ContextDAO.APPROVED);
		
		List<QuestionDAO> questions = new ArrayList<QuestionDAO>();
		
		QuestionDAO q = new QuestionDAO();
		
		q.setQuestionNumber(1);
		q.setCorrectAnswerCount(0);
		q.setQuestion("What is your name?");
		q.setAnswer("c");
		Map<String,String> m1 = new HashMap<String,String>();
		m1.put("a","ben");
		m1.put("b","bon");
		m1.put("c","don");
		q.setSelections(m1);
		questions.add(q);
		
		q = new QuestionDAO();
		q.setQuestionNumber(2);
		q.setCorrectAnswerCount(0);
		q.setQuestion("What is cat?");
		q.setAnswer("c");		
		m1 = new HashMap<String,String>();
		m1.put("a","animal");
		m1.put("b","plant");
		m1.put("c","pet");	
		q.setSelections(m1);
		questions.add(q);
		
		q = new QuestionDAO();
		q.setQuestionNumber(3);
		q.setCorrectAnswerCount(0);
		q.setQuestion("What is phone?");
		q.setAnswer("a");		
		m1 = new HashMap<String,String>();
		m1.put("a","fon");
		m1.put("b","glass");
		m1.put("c","pet");	
		q.setSelections(m1);
		questions.add(q);
		

		DataHandler handler = DataHandler.getInstance(DataHandler.Types.EXCEL);
		
		@SuppressWarnings("unchecked")

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		
		
		InputStream is = null; 
		
		if(application.getExcelFile() != null && !application.getExcelFile().isEmpty() ) {
			is = new FileInputStream(application.getExcelFile());
		} else {
			is = classloader.getResourceAsStream("excel/sample.xlsx");
		}
			
		
		
		@SuppressWarnings("unchecked")
		List<QuestionDAO> questions2 = (List<QuestionDAO>)handler.loadData(is);
		
		
		logger.debug(questions2.toString());
		questions = questions2;
		
		SessionDAO session = new SessionDAO();
		session.setMemberNumber("aaaa");
		session.setExamCode("bbbb");
		session.setStartDate(new Date());
		session.setQuestions(questions);
		session.setQuestionSetTotalValue(8); //i.e 8 or 2 or 10
		session.setTotalQuestion(questions.size());
		session.setTotalQuestionRunningValue(1);
		session.setNumberOfSetsDone(1);
		
		application.setQandaSessionDAO(session);
		
		// 1. loop thru all items and find setCorrectAnswerCount < 2
		// 2. add it to array list of integer, the identified index id on #1
		// 3. if result is > 1, shuffle it
		// 4. get the first index
		
		
		logger.debug("getWrongAnswersIndexes: "+session.getWrongAnswers());
		logger.debug("getWrongAnswersRunningValue: "+session.getWrongAnswersRunningValue());
		logger.debug("getQuestionSetRunningValue: "+session.getQuestionSetRunningValue());
		logger.debug("getQuestionSetTotalValue: "+session.getQuestionSetTotalValue());
		
		if ( questions.size() > 0 ) {
			session.setQuestionSetRunningValue(1);
			application.setStatus(ContextDAO.QUESTION_AVAILABLE);
			QAUtils.evaluteQuestionSet(session, questions, application );
		
		} // no else needed
		
		application.setSessiondId(UUID.randomUUID().toString());
		
		this.getPersistenceService().saveSessionData(application);

	}

}
