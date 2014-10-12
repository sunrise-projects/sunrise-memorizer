package com.sunriseprojects.memorizer.core.quiz;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.data.DataHandler;

public class ExcelQuiz implements Quiz {

	static Logger logger = Logger.getLogger(ExcelQuiz.class);
	
	Object code = null;
	Object description = null;
	Object startDate = null;
	
	final String questionList = "quiz-question-list";
	final String questionListAnswer = "quiz-question-answers";
	
	@Override
	public void setCode(Object code) {
		this.code = code;
	}

	@Override
	public void setDescription(Object description) {
		this.description = description;
	}

	@Override
	public void setStartDate(Object startDate) {
		this.startDate = startDate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processInput(Object inputFilename, Object uniqueIdOut, Object questionListOut) {
		
		DataHandler handler = DataHandler.getInstance(DataHandler.Types.EXCEL);		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();	
		InputStream is = null; 
		
		if (inputFilename instanceof String ) {
			try {
				logger.info(inputFilename.toString());
				is = new FileInputStream(inputFilename.toString());
			} catch (FileNotFoundException e) {
				logger.error(e.toString());
			}
		} else {
			logger.info("loading sample");
			is = classloader.getResourceAsStream("excel/sample.xlsx");
		}
		@SuppressWarnings("unchecked")
		List<QuestionDAO> questions2 = (List<QuestionDAO>)handler.loadData(is);
		logger.info(questions2.size());
		logger.info(questions2.toString());
		Collections.shuffle(questions2);
		logger.info(questions2.toString());
		String uniqueId = UUID.randomUUID().toString();
		
		List<QuizAnswer> quizAnswers = new ArrayList<QuizAnswer>();
		for(QuestionDAO ans : questions2 ) {
			int qNum = ans.getQuestionNumber();
			String an = ans.getAnswer();
			QuizAnswer b = new QuizAnswer();
			b.setQuestionNumber(qNum);
			b.setQuestionAnswer(an);
			b.setCorrect(false);
			quizAnswers.add(b);			
		}
		
		saveData(questionList, uniqueId, questions2);
		
		
		
		saveData(questionListAnswer, uniqueId, quizAnswers);
		
		if (uniqueIdOut instanceof StringBuilder ) {
			((StringBuilder)uniqueIdOut).append(uniqueId);
		}
		
		if(questionListOut instanceof List<?> ) {
			((List<QuestionDAO>) questionListOut).addAll(questions2);
		}

	}
	
	private boolean saveData(String prefix, String uniqueId, Object data) {
		boolean ret = true;
		FileOutputStream fout=null;
		try {
			String temp = System.getProperty("java.io.tmpdir");
			String fname = temp+"/"+prefix+"-"+uniqueId+".tmp";
			logger.info("set:"+fname);
			fout = new FileOutputStream(fname);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(data);
			oos.close();
						
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
	
	private Object getSessionData(String prefix, String uniqueId) {
		
		String temp = System.getProperty("java.io.tmpdir");
		FileInputStream fin;
		Object obj = null;
		try {
			String fname = temp+"/"+prefix+"-"+uniqueId+".tmp";
			logger.info("get:"+fname);
			fin = new FileInputStream(fname);
			ObjectInputStream ois = new ObjectInputStream(fin);
			obj = ois.readObject();
			fin.close();
		} catch (IOException o) {
			logger.error(o.toString());
		} catch (ClassNotFoundException c) {
			logger.error(c.toString());
		}
		return obj;		
	}	

	@Override
	public Object showQuestions(Object uniqueId) {
		return getSessionData(questionList,uniqueId.toString());
	}

	@Override
	public void saveAnswer(Object uniqueId, Object questionNumber, Object answerValue) {
		logger.info(uniqueId.toString()+":"+questionNumber.toString()+":"+answerValue.toString());
		logger.info("fetch answers");
		List<QuizAnswer> quizAnswers = (List<QuizAnswer>)getSessionData(questionListAnswer,uniqueId.toString());
		logger.info(quizAnswers.toString());
		int questNum = Integer.valueOf(questionNumber.toString());
		for(QuizAnswer quizAnswer : quizAnswers ) {
			if (quizAnswer.getQuestionNumber() == questNum )  {				
				String ans = quizAnswer.getQuestionAnswer();
				String ansValue = answerValue.toString();
				quizAnswer.setTesterAnswer(answerValue.toString());
				if(ans.equalsIgnoreCase(ansValue)) {
					quizAnswer.setCorrect(true);
				}
				break;
			}				
		}
		//save data
		saveData(questionListAnswer, uniqueId.toString(), quizAnswers);
		logger.info(quizAnswers.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void computeResult(Object uniqueId, Object questionListOut, Object totalCount, Object totalCorrect, Object average) {
		List<QuizAnswer> quizAnswersItems = (List<QuizAnswer>)getSessionData(questionListAnswer,uniqueId.toString());
		List<QuestionDAO> questionListItems = (List<QuestionDAO>)getSessionData(questionList,uniqueId.toString());
		int correct = 0;
		for(QuestionDAO questionDAO : questionListItems ) {
			for (QuizAnswer quizAnswer : quizAnswersItems ) {
				if(quizAnswer.getQuestionNumber() == questionDAO.getQuestionNumber() ) {
					questionDAO.setTesterAnswer(quizAnswer.getTesterAnswer());
					questionDAO.setTesterCorrect(quizAnswer.isCorrect());
					if(quizAnswer.isCorrect()) {
						correct++;
					}
					break;
				}
			}
		}
		if(questionListOut instanceof List<?> ) {
			((List<QuestionDAO>) questionListOut).addAll(questionListItems);
		}
		logger.info(questionListItems.toString());
		logger.info(questionListItems.size());
		
		int totalCountL = questionListItems.size();
		
		int totalCorrectL = correct;
		
		double averageL = (( totalCorrectL / (totalCountL*1.0) ) * 100 );
		
		if(totalCount instanceof StringBuilder) {
			((StringBuilder) totalCount).append(String.valueOf(totalCountL));
		}
		
		if(totalCorrect instanceof StringBuilder) {
			((StringBuilder) totalCorrect).append(String.valueOf(totalCorrectL));
		}
		
		if(average instanceof StringBuilder) {
			((StringBuilder) average).append(String.valueOf(averageL));
		}
		
	}
	
	
	

	@Override
	public Object getCode() {
		return this.code;
	}

	@Override
	public Object getDescription() {
		return this.description;
	}

	@Override
	public Object getStartDate() {
		return this.startDate;
	}

}
