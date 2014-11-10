package com.sunriseprojects.profile.web.rest.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunriseprojects.profile.core.service.ProfileManager;
import com.sunriseprojects.profile.web.contants.ProfileRestConstants;
import com.sunriseprojects.profile.web.model.ProfileModel;

@Controller
public class ProfileRestController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileRestController.class);
	
	@Autowired ProfileManager profileManager;
	
    @RequestMapping(value = ProfileRestConstants.SIGNUP, method = RequestMethod.POST)
    public @ResponseBody ProfileModel doSignup(@RequestBody ProfileModel profile) {
        logger.info("Start doSignup");
        ProfileModel prof = new ProfileModel();
        prof.setEmail(profile.getEmail());
        prof.setCreatedDate(new Date());        
        String[] res = profileManager.signup("firstname", "lastnme", profile.getEmail(), profile.getPasswd());
        prof.setStatusCode(Integer.valueOf(res[0]));
        prof.setStatusText(res[1]);
        
        return prof;
    }
    
}
