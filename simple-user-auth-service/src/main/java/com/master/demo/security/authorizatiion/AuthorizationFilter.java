package com.master.demo.security.authorizatiion;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.master.demo.shared.constants.Constants;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//get authorization header and check its validity
		String header = request.getHeader(Constants.HEADER);
		if (header == null || !header.startsWith(Constants.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
// now get the authentication token		
		UsernamePasswordAuthenticationToken authToken = getAuthenticationToken(request);
		SecurityContextHolder.getContext().setAuthentication(authToken);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
//		read token
		String token = request.getHeader(Constants.HEADER);
		if (token != null) {
//			remove prefix
			token = token.replace(Constants.TOKEN_PREFIX, "");
//			get username from the token
			String user = Jwts.parser().setSigningKey(Constants.getTokenSecret()).parseClaimsJws(token).getBody()
					.getSubject();
			
			if (user!=null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}

		return null;
	}
}
