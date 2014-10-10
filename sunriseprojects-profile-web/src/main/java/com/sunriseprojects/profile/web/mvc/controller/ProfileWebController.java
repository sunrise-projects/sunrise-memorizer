package com.sunriseprojects.profile.web.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.sunriseprojects.profile.web.contants.ProfileRestConstants;
import com.sunriseprojects.profile.web.contants.ProfileWebConstants;
import com.sunriseprojects.profile.web.model.ProfileModel;



@Controller
public class ProfileWebController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileWebController.class);
 
	@Value("${profile.rest.url}")
	private String restUrl;
	
	@RequestMapping(value = ProfileWebConstants.SIGNUP, method = RequestMethod.GET)
	public String showSignupPage(ModelMap model) {
 		model.addAttribute("message", "Member signup");
		return "signup";
	}

	@RequestMapping(value = ProfileWebConstants.SIGNUP, method = RequestMethod.POST)
	public String signupSubmit(ModelMap model, @RequestParam("email") String email, @RequestParam("passwd") String passwd, @RequestParam("passwd2") String passwd2) { 		
		logger.info(email);
		logger.info(passwd);
		logger.info(passwd2);		
		ProfileModel prof = new ProfileModel();
		prof.setEmail(email);
		prof.setPasswd(passwd);		
        RestTemplate restTemplate = new RestTemplate();
        ProfileModel profile = restTemplate.postForObject(restUrl+ProfileRestConstants.SIGNUP, prof, ProfileModel.class);
		model.addAttribute("message", "Thanks");
		model.addAttribute("profile", profile);
		return "signup_welcome";
	}	
}
