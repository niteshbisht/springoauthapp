package com.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.live.api.Live;
import org.springframework.social.live.api.LiveProfile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LiveController {
	@Autowired
	Live live;
	@RequestMapping(value = "/api/live/details", method = RequestMethod.GET, produces = "application/json")
    public String getSocialDetails() {
    	LiveProfile userResult = live.userOperations().getUserProfile();
    	if(userResult!=null){
    		return "{'username':'"+userResult.getFirstName()+"'}";
    	}else{
    		return "{'username':'sample'}";
    	}     
    }
    @RequestMapping(value = "/api/live/update", method = RequestMethod.GET, produces = "application/json")
    public void updateSocialDetails() {
    	
    }
}
