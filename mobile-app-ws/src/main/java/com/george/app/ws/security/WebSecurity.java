package com.george.app.ws.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.george.app.ws.service.UserService;

//this is a web security class and hence add annotation
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;
	
//	constructor
	public WebSecurity(UserService userDetailsService,BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
		this.userDetailsService =userDetailsService;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() //disables it for
		.authorizeRequests().antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL) // authorizes this service by default
		.permitAll().anyRequest().authenticated()// authenticate all other services by default
		.and()
		.addFilter(getAuthenticationFilter());
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  auth.userDetailsService(userDetailsService).passwordEncoder(bcryptPasswordEncoder);// set password encoder
	}	

public AuthenticationFilter getAuthenticationFilter() throws Exception{
	final AuthenticationFilter filter =  new AuthenticationFilter(authenticationManager());
	filter.setFilterProcessesUrl("/users/login");
	return filter;
}
}
