package com.server.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.live.api.Live;
import org.springframework.social.live.connect.LiveConnectionFactory;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import com.server.security.AutoSignUpHandler;
import com.server.security.SimpleUsersConnectionRepository;
import com.server.security.UserAuthenticationUserIdSource;
import com.server.service.SocialUserService;

@Configuration
@EnableSocial
public class StatelessSocialConfig extends SocialConfigurerAdapter {

	private static final Logger logger = Logger.getLogger(StatelessSocialConfig.class);
	@Autowired
	private SocialUserService userService;
	
	@Value(value = "${facebook.appKey}")
	String facebookAppKey;
	@Value(value = "${facebook.appSecret}")
	String facebookAppSecret;
	@Value(value = "${twitter.accessToken}")
	String twitterAccessToken;
	@Value(value = "${twitter.tokenSecret}")
	String twitterAccessSecret;
	@Value(value = "${linkedin.appId}")
	String linkedinappId;
	@Value(value = "${linkedin.appSecret}")
	String linkedinappSecret;
	@Value(value = "${live.appId}")
	String liveappId;
	@Value(value = "${live.appSecret}")
	String liveappSecret;
	@Value(value = "${github.appId}")
	String githubAppId;
	@Value(value = "${github.appSecret}")
	String githubAppSecret;
	@Value(value = "${google.appId}")
	String googleAppId;
	@Value(value = "${google.appSecret}")
	String googleAppSecret;
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
		logger.info("facebook app key "+facebookAppKey);
		logger.info("facebook app secret "+facebookAppSecret);
		cfConfig.addConnectionFactory(new FacebookConnectionFactory(
				facebookAppKey,
				facebookAppSecret));
		logger.info("twitter access token "+twitterAccessToken);
		logger.info("twitter access secret "+twitterAccessSecret);
		cfConfig.addConnectionFactory(new TwitterConnectionFactory(
				twitterAccessToken,
				twitterAccessSecret));
		logger.info("linkedin appId "+linkedinappId);
		logger.info("linkedin appSecret "+linkedinappSecret);
		cfConfig.addConnectionFactory(new LinkedInConnectionFactory(
				linkedinappId,
				linkedinappSecret));
		logger.info("live appId "+liveappId);
		logger.info("live appSecret "+liveappSecret);
		cfConfig.addConnectionFactory(new LiveConnectionFactory(
				liveappId,
				liveappSecret));
		logger.info("google appId "+googleAppId);
		logger.info("google appSecret "+googleAppSecret);
		cfConfig.addConnectionFactory(new GoogleConnectionFactory(
				googleAppId,
				googleAppSecret));
		logger.info("github appId "+githubAppId);
		logger.info("github appSecret "+githubAppSecret);
		cfConfig.addConnectionFactory(new GitHubConnectionFactory(
				githubAppId,
				githubAppSecret));
	}

	@Override
	public UserIdSource getUserIdSource() {
		// retrieve the UserId from the UserAuthentication in the security context
		return new UserAuthenticationUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		SimpleUsersConnectionRepository usersConnectionRepository =
				new SimpleUsersConnectionRepository(userService, connectionFactoryLocator);

		// if no local user record exists yet for a facebook's user id
		// automatically create a User and add it to the database
		usersConnectionRepository.setConnectionSignUp(new AutoSignUpHandler());
		return usersConnectionRepository;
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Facebook facebook(ConnectionRepository repository) {
		Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
		return connection != null ? connection.getApi() : null;
	}
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Twitter twitter(ConnectionRepository repository) {
		Connection<Twitter> connection = repository.findPrimaryConnection(Twitter.class);
		return connection != null ? connection.getApi() : null;
	}
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public LinkedIn linkedIn(ConnectionRepository repository) {
		Connection<LinkedIn> connection = repository.findPrimaryConnection(LinkedIn.class);
		return connection != null ? connection.getApi() : null;
	}
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Live live(ConnectionRepository repository) {
		Connection<Live> connection = repository.findPrimaryConnection(Live.class);
		return connection != null ? connection.getApi() : null;
	}
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Google google(ConnectionRepository repository) {
		Connection<Google> connection = repository.findPrimaryConnection(Google.class);
		return connection != null ? connection.getApi() : null;
	}
}
