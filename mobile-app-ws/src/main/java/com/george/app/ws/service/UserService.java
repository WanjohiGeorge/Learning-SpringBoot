package com.george.app.ws.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.george.app.ws.shared.dto.UserDto;


public interface UserService extends UserDetailsService{
// Create user 
	UserDto createUser(UserDto user);
	UserDto getUser(String email);
}
