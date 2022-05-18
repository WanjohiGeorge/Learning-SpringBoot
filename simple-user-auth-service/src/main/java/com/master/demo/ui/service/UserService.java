package com.master.demo.ui.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.master.demo.shared.dto.UserDTO;

public interface UserService extends UserDetailsService{
// creates, deletes, updates new users and other dirty work
	UserDTO createUser(UserDTO user);
	UserDTO getUSer(String email);UserDTO getUserByUserId(String userID);
	void deleteUser(String userID);
	
	List<UserDTO> getUsers(int page, int limit);
}
