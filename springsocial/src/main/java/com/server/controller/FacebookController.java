package com.server.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.server.config.StatelessAuthenticationSecurityConfig;

@RestController
public class FacebookController {
    @Autowired
    Facebook facebook;

    @RequestMapping(value = "/api/facebook/details", method = RequestMethod.GET, produces = "application/json")
    public String getSocialDetails() {
    	User userResult = facebook.userOperations().getUserProfile();
    	if(userResult!=null){
    		return "{'username':'"+userResult.getFirstName()+"'}";
    	}else{
    		return "{'username':'sample'}";
    	}     
    }
    @RequestMapping(value = "/api/facebook/update", method = RequestMethod.GET, produces = "application/json")
    public void updateSocialDetails() {
    	     
    }
}
