package com.sunriseprojects.memorizer.web.mvc.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.sunriseprojects.memorizer.web.constants.QuestionMVCConstants;
import com.sunriseprojects.memorizer.web.rest.model.QuestionModel;
import com.sunriseprojects.memorizer.web.rest.model.QuizModel;
 
@Controller
public class QuizMvcController {

	@Value("${rest.url}")
	private String restUrl;

	@Value("${excel.location}")
	private String excelLocation;
	
	@RequestMapping(value = QuestionMVCConstants.QUIZ_WELCOME, method = RequestMethod.GET)
	public String quesitonWelcome(ModelMap model) {
 
		Map<String,String> map = new HashMap<String,String>();
		File folder = new File(excelLocation);
		File[] listOfFiles = folder.listFiles();
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	if(listOfFiles[i].getName().contains("uploaded")) continue;
		        System.out.println("File " + listOfFiles[i].getName());		        
		        byte[]  bytesEncoded = Base64.encodeBase64((listOfFiles[i].getName()).getBytes());
		        
		        map.put(new String(bytesEncoded), listOfFiles[i].getName() );
		        
		        
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }		
		model.addAttribute("fileList", map);
		model.addAttribute("message", "Ready to start question?");
		return "quiz_welcome";
	}

	@RequestMapping(value = QuestionMVCConstants.QUIZ_RESULTS, method = RequestMethod.GET)
	public String quizResults(ModelMap model, @PathVariable("uniqueId") String uniqueId) {
 		
        RestTemplate restTemplate = new RestTemplate();
        QuizModel quiz = restTemplate.getForObject(restUrl+"/rest/quiz/results/"+uniqueId, QuizModel.class);

        model.addAttribute("uniqueid", quiz.getUniqueId());
        model.addAttribute("questionlist", quiz.getQuestionList());
        model.addAttribute("totalCorrect", quiz.getTotalCorrect());
        model.addAttribute("totalCount", quiz.getTotalCount());
        model.addAttribute("average", quiz.getAverage());
		model.addAttribute("message", "Question detail below?");
		return "quiz_result";
	}	

	@RequestMapping(value = QuestionMVCConstants.QUIZ_START, method = RequestMethod.GET)
	public String quizStartFile(ModelMap model, @PathVariable("file") String file) {
 		
        RestTemplate restTemplate = new RestTemplate();
        QuizModel quiz = restTemplate.getForObject(restUrl+"/rest/quiz/start/"+file, QuizModel.class);

        model.addAttribute("uniqueid", quiz.getUniqueId());
        model.addAttribute("questionlist", quiz.getQuestionList());
		model.addAttribute("message", "Question detail below?");
		return "quiz_start";
	}	

	
	@RequestMapping(value = QuestionMVCConstants.QUIZ_UPLOAD, method = RequestMethod.POST)
	public String questionUploadFile(ModelMap model, @RequestParam("name") String name, 
			@RequestParam("file") MultipartFile file) {		
        String fname = name+"-uploaded"+UUID.randomUUID().toString();
        System.out.println(fname);
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(excelLocation+"/"+fname)));
                stream.write(bytes);
                stream.close();
                System.out.println("You successfully uploaded " + name + " into " + name );
            } catch (Exception e) {
                System.out.println("You failed to upload " + name + " => " + e.getMessage());
            }
        } else {
        	System.out.println( "You failed to upload " + name + " because the file was empty.");
        }		
        byte[]  bytesEncoded = Base64.encodeBase64(fname.getBytes());
        String fileName = new String(bytesEncoded);
        RestTemplate restTemplate = new RestTemplate();
        QuizModel quiz = restTemplate.getForObject(restUrl+"/rest/quiz/start/"+fileName, QuizModel.class);

        model.addAttribute("uniqueid", quiz.getUniqueId());
        model.addAttribute("questionlist", quiz.getQuestionList());
		model.addAttribute("message", "Question detail below?");
		return "quiz_start";
	}

	
}
