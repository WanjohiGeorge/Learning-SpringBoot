package com.master.demo.ui.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.master.demo.shared.dto.UserDTO;

public interface UserService extends UserDetailsService{
// creates, deletes, updates new users and other dirty work
	UserDTO createUser(UserDTO user);
	UserDTO getUSer(String email);UserDTO getUserByUserId(String userID);
}
