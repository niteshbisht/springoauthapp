package com.server.repository;

import java.util.List;

import com.server.entity.User;

public interface UserDao{

	com.server.entity.User findByUsername(String username);

	com.server.entity.User findById(String userId);

	com.server.entity.User findByProviderIdAndProviderUserId(String providerId, String providerUserId);

	void save(User user);

	List<User> findAll();

	void updateUser(User user);
	
	
}
