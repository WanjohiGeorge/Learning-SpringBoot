package com.master.demo.security.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.demo.SpringApplicationContext;
import com.master.demo.shared.constants.Constants;
import com.master.demo.shared.dto.UserDTO;
import com.master.demo.ui.request.models.login.UserLoginRequestModel;
import com.master.demo.ui.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
//			create authentication credentials
			UserLoginRequestModel credentials = new ObjectMapper().readValue(request.getInputStream(),
					UserLoginRequestModel.class);
//			try authenticating
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(),
					credentials.getPassword(), new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((User) authResult.getPrincipal()).getUsername();

		String token = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + Constants.AUTH_DURATION))
				.signWith(SignatureAlgorithm.HS512, Constants.getTokenSecret()).compact();

		UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
		UserDTO userDTO = userService.getUSer(username);
		
		response.addHeader("UserId", userDTO.getUserID());

		response.addHeader(Constants.HEADER, Constants.TOKEN_PREFIX + token);
	}
}
