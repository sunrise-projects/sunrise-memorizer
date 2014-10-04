package com.sunriseprojects.memorizer.web.mvc.spring.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.sunriseprojects.memorizer.web.rest.spring.controller.EmpRestURIConstants;
import com.sunriseprojects.memorizer.web.rest.spring.model.Employee;
import com.sunriseprojects.memorizer.web.rest.spring.model.QuestionModel;
 
@Controller
public class QuestionMVCController {

	@Value("${rest.url}")
	private String restUrl;

	@Value("${excel.location}")
	private String excelLocation;
	
	@RequestMapping(value = QuestionMvcURIConstants.WELCOME_QUESTION, method = RequestMethod.GET)
	public String quesitonWelcome(ModelMap model) {
 
		Map<String,String> map = new HashMap<String,String>();
		File folder = new File(excelLocation);
		File[] listOfFiles = folder.listFiles();
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());		        
		        byte[]  bytesEncoded = Base64.encodeBase64((listOfFiles[i].getName()).getBytes());
		        
		        map.put(new String(bytesEncoded), listOfFiles[i].getName() );
		        
		        
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }		
		model.addAttribute("fileList", map);
		model.addAttribute("message", "Ready to start question?");
		return "question_welcome";
	}

	@RequestMapping(value = QuestionMvcURIConstants.START_QUESTION_FILE, method = RequestMethod.GET)
	public String quesitonStartFile(ModelMap model, @PathVariable("file") String file) {
 		
        RestTemplate restTemplate = new RestTemplate();
        QuestionModel question = restTemplate.getForObject(restUrl+"/rest/question/start/"+file, QuestionModel.class);

        model.addAttribute("question", question);
		model.addAttribute("message", "Question detail below?");
		return "question_start";
	}
	
	@RequestMapping(value = QuestionMvcURIConstants.START_QUESTION, method = RequestMethod.GET)
	public String quesitonStart(ModelMap model) {
 		
        RestTemplate restTemplate = new RestTemplate();
        QuestionModel question = restTemplate.getForObject(restUrl+"/rest/question/start", QuestionModel.class);

        model.addAttribute("question", question);
		model.addAttribute("message", "Question detail below?");
		return "question_start";
	}

	@RequestMapping(value = QuestionMvcURIConstants.NEXT_QUESTION, method = RequestMethod.GET)
	public String nextQuestion(ModelMap model, @PathVariable("sessionId") String sessionId) {
 		
        RestTemplate restTemplate = new RestTemplate();
        QuestionModel question = restTemplate.getForObject(restUrl+"/rest/question/next/"+sessionId, QuestionModel.class);

        model.addAttribute("question", question);
		model.addAttribute("message", "Question detail below?");
		return "question_next";
	}

	@RequestMapping(value = QuestionMvcURIConstants.REVIEW_ANSWER, method = RequestMethod.GET)
	public String showAnswers(ModelMap model, @PathVariable("sessionId") String sessionId) {
 		
        RestTemplate restTemplate = new RestTemplate();
        QuestionModel question = restTemplate.getForObject(restUrl+"/rest/question/review/"+sessionId, QuestionModel.class);

        model.addAttribute("question", question);
		model.addAttribute("message", "Review answer below?");
		return "review_answer";
	}

	
}