package com.sunriseprojects.memorizer.core.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.dao.SessionDAO;
import com.sunriseprojects.memorizer.core.services.QAPersistenceAwareAction;


public class CheckAnswerPositiveAction extends QAPersistenceAwareAction {

	static Logger logger = Logger.getLogger(CheckAnswerPositiveAction.class);
	
	protected void doExecute(Object arg) throws Exception {

		ContextDAO ctx = (ContextDAO) arg;

		SessionDAO qASessionDAO = this.getPersistenceService().getSessionData(
				ctx);

		QuestionDAO dao = null;
		for (QuestionDAO qAQuestionDAO : qASessionDAO.getQuestions()) {
			if (qAQuestionDAO.getQuestionNumber() == ctx
					.getQuestionSessionNumber()) {
				dao = qAQuestionDAO;
				break;
			}
		}

		// logger.debug(ctx.getQuestionAnswer());
		// logger.debug(dao.getQuestion());
		// logger.debug(dao.getAnswer());
		// logger.debug(dao.getSelections());

		// logger.debug(ctx.getQuestionAnswer().equals(dao.getAnswer()));
		// logger.debug(dao.getCorrectAnswerCount());

		if (ctx.getQuestionAnswer().equals(dao.getAnswer())) {
			int correct = dao.getCorrectAnswerCount();
			int c = correct + 1;
			dao.setCorrectAnswerCount(c);
			this.getPersistenceService().saveData(ctx, qASessionDAO);
			logger.debug("getCorrectAnswerCount: "+dao.getCorrectAnswerCount());
			ctx.setStatus(ContextDAO.ANSWER_IS_CORRECT);

		} else {
			// logger.debug(dao.getCorrectAnswerCount());
			int c = 0;
			dao.setCorrectAnswerCount(c);
			this.getPersistenceService().saveData(ctx, qASessionDAO);
			// logger.debug(dao.getCorrectAnswerCount());
			ctx.setStatus(ContextDAO.ANSWER_NOT_CORRECT);

			// if answer not correct, insert the q# to wrongAnswer
			if (qASessionDAO.getWrongAnswers() == null) {
				LinkedList<Integer> wrongAnswers = new LinkedList<Integer>();
				wrongAnswers.add(ctx.getQuestionSessionNumber());
				qASessionDAO.setWrongAnswers(wrongAnswers);
			} else {
				qASessionDAO.getWrongAnswers().add(
						ctx.getQuestionSessionNumber());
			}
			logger.debug("Wrong answers: "
					+ qASessionDAO.getWrongAnswers().toString());
			
			// persist
			this.getPersistenceService().saveData(ctx, qASessionDAO);

		}

	}

}
