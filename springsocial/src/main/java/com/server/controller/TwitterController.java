package com.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwitterController {
	@Autowired
	Twitter twitter;
	@RequestMapping(value = "/api/twitter/details", method = RequestMethod.GET, produces = "application/json")
    public String getSocialDetails() {
    	TwitterProfile userResult = twitter.userOperations().getUserProfile();
    	if(userResult!=null){
    		return "{'username':'"+userResult.getName()+"'}";
    	}else{
    		return "{'username':'sample'}";
    	}     
    }
    @RequestMapping(value = "/api/twitter/update", method = RequestMethod.GET, produces = "application/json")
    public void updateSocialDetails() {
    	
    }
}
