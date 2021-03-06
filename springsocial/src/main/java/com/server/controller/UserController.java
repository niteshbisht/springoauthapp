package com.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.server.entity.User;
import com.server.entity.UserRole;
import com.server.repository.UserDao;
import com.server.security.UserAuthentication;

@RestController
public class UserController {

	@Autowired
	UserDao userRepository;

	@RequestMapping(value = "/api/user/current", method = RequestMethod.GET)
	public User getCurrent() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof UserAuthentication) {
			return ((UserAuthentication) authentication).getDetails();
		}
		return new User(); //anonymous user support
	}

	@RequestMapping(value = "/admin/api/user/{user}/grant/role/{role}", method = RequestMethod.POST)
	public ResponseEntity<String> grantRole(@PathVariable User user, @PathVariable UserRole role) {
		if (user == null) {
			return new ResponseEntity<>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		user.grantRole(role);
		//userRepository.saveAndFlush(user);
		return new ResponseEntity<>("role granted", HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/api/user/{user}/revoke/role/{role}", method = RequestMethod.POST)
	public ResponseEntity<String> revokeRole(@PathVariable User user, @PathVariable UserRole role) {
		if (user == null) {
			return new ResponseEntity<>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		user.revokeRole(role);
		//userRepository.saveAndFlush(user);
		return new ResponseEntity<>("role revoked", HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/api/user", method = RequestMethod.GET)
	public List<User> list() {
		return userRepository.findAll();
	}
}
