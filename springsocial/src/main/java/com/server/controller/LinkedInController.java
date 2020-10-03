package com.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.userinfo.GoogleUserInfo;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkedInController {

	@Autowired
	LinkedIn linkedin;
	@RequestMapping(value = "/api/linkedin/details", method = RequestMethod.GET, produces = "application/json")
    public String getSocialDetails() {
    	LinkedInProfile userResult = linkedin.profileOperations().getUserProfile();
    	if(userResult!=null){
    		return "{'username':'"+userResult.getFirstName()+"'}";
    	}else{
    		return "{'username':'sample'}";
    	}     
    }
    @RequestMapping(value = "/api/linkedin/update", method = RequestMethod.GET, produces = "application/json")
    public void updateSocialDetails() {
    	
    }
}
