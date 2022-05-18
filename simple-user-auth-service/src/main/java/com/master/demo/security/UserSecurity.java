package com.master.demo.security;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.master.demo.security.authentication.AuthenticationFilter;
import com.master.demo.security.authorizatiion.AuthorizationFilter;
import com.master.demo.shared.constants.Constants;
import com.master.demo.ui.service.UserService;

@EnableWebSecurity
public class UserSecurity extends WebSecurityConfigurerAdapter {
	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userDetailsService = userDetailsService;
	}
//	do configurations

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, Constants.SIGN_UP_URL).permitAll()
				.anyRequest().authenticated().and().addFilter(getAuthenticationFilter())
				.addFilter(new AuthorizationFilter(authenticationManager())).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	public AuthenticationFilter getAuthenticationFilter() throws Exception {
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());

		filter.setFilterProcessesUrl(Constants.SIGN_IN_URL);
		return filter;
	}
}
