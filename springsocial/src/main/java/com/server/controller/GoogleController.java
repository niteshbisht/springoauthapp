package com.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.userinfo.GoogleUserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleController {
	@Autowired
	Google google;
    @RequestMapping(value = "/api/google/details", method = RequestMethod.GET, produces = "application/json")
    public String getSocialDetails() {
    	GoogleUserInfo userResult = google.userOperations().getUserInfo();
    	if(userResult!=null){
    		return "{'username':'"+userResult.getFirstName()+"'}";
    	}else{
    		return "{'username':'sample'}";
    	}     
    }
    @RequestMapping(value = "/api/google/update", method = RequestMethod.GET, produces = "application/json")
    public void updateSocialDetails() {
    	
    }
}
