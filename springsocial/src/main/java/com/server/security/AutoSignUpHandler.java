package com.server.security;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.server.entity.User;
import com.server.entity.UserRole;
import com.server.repository.UserDao;

@Component("connectionSignUp")
public class AutoSignUpHandler implements ConnectionSignUp {
	private static final Logger logger = Logger.getLogger(AutoSignUpHandler.class);
    
    private UserDao userDao;
    
    @Autowired
    public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

    
    @Override
    @Transactional
    public String execute(final Connection<?> connection) {
        final User user = new User();
        String userIdForuser = UUID.randomUUID().toString();
        user.setId(userIdForuser);
        logger.info("Generated UUID "+userIdForuser);
        String userName =connection.fetchUserProfile().getFirstName();
        user.setUsername(userName);
        user.setProviderId(connection.getKey().getProviderId());
        user.setProviderUserId(connection.getKey().getProviderUserId());
        user.setAccessToken(connection.createData().getAccessToken());
        return grantRoles(user);
    }

    
	private String grantRoles(final User user) {
      logger.info("Grant Roles here to the new user");
       String userId = null;
       User userNew = userDao.findByUsername(user.getUsername());
       if (userNew == null) {
    	   logger.info("New User Found");
                user.grantRole(UserRole.USER);
                userDao.save(user);
                userId = user.getUserId();
        }else{
        	logger.info("Existing User");
        	userId = userNew.getUserId();
        }
        return userId;
    }
}
