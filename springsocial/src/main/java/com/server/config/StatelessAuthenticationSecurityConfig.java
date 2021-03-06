package com.server.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.social.UserIdSource;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import com.server.security.SocialAuthenticationSuccessHandler;
import com.server.security.StatelessAuthenticationFilter;
import com.server.service.SocialUserService;

@EnableWebSecurity
@Configuration
@Order(1)
public class StatelessAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger logger = Logger.getLogger(StatelessAuthenticationSecurityConfig.class);
	@Autowired
	private SocialAuthenticationSuccessHandler socialAuthenticationSuccessHandler;

	@Autowired
	private StatelessAuthenticationFilter statelessAuthenticationFilter;

	@Autowired
	private UserIdSource userIdSource;

	@Autowired
	private SocialUserService userService;

	public StatelessAuthenticationSecurityConfig() {
		super(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Set a custom successHandler on the SocialAuthenticationFilter
		logger.info("initializing security");
		final SpringSocialConfigurer socialConfigurer = new SpringSocialConfigurer();
		socialConfigurer.addObjectPostProcessor(new ObjectPostProcessor<SocialAuthenticationFilter>() {
			@Override
			public <O extends SocialAuthenticationFilter> O postProcess(O socialAuthenticationFilter) {
				socialAuthenticationFilter.setAuthenticationSuccessHandler(socialAuthenticationSuccessHandler);
				return socialAuthenticationFilter;
			}
		});

		http.exceptionHandling().and().anonymous().and().servletApi().and()
				.authorizeRequests()
				//allow anonymous font and template requests
				.antMatchers("/").permitAll()
				.antMatchers("/favicon.ico").permitAll()
				.antMatchers("/**/**").permitAll()
				.antMatchers("/index.html").permitAll()
				//allow anonymous calls to social login
				.antMatchers("/auth/**","/signin/**").permitAll()
				//allow anonymous GETs to API
				.antMatchers(HttpMethod.GET, "/api/**").permitAll()
				//defined Admin only API area
				.antMatchers("/admin/**").hasRole("ADMIN")
				//all other request need to be authenticated
				.antMatchers(HttpMethod.GET, "/api/users/current/details").hasRole("USER")
				.anyRequest().hasRole("USER").and()
				// add custom authentication filter for complete stateless JWT based authentication
				.addFilterBefore(statelessAuthenticationFilter, AbstractPreAuthenticatedProcessingFilter.class)
				// apply the configuration from the socialConfigurer (adds the SocialAuthenticationFilter)
				.apply(socialConfigurer.userIdSource(userIdSource)).and().headers().cacheControl();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	@Override
	protected SocialUserService userDetailsService() {
		return userService;
	}
}
