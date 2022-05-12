package com.master.demo.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.master.demo.shared.dto.UserDTO;
import com.master.demo.ui.request.models.UserRequestModel;
import com.master.demo.ui.response.models.UserResponseModel;
import com.master.demo.ui.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/")

public class UserController {
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping
	public UserResponseModel getUser(@RequestBody UserRequestModel userRequestModel) {		
		UserResponseModel returnValue =new UserResponseModel();
		BeanUtils.copyProperties(userRequestModel, returnValue);
		return returnValue;
	}
	
	@PostMapping
	public UserResponseModel setUser(@RequestBody UserRequestModel userRequestModel) {		
		UserResponseModel returnValue =new UserResponseModel();
		
		UserDTO userDTO =  new UserDTO();
		BeanUtils.copyProperties(userRequestModel, userDTO);
		
		UserDTO createdUser = userService.createUser(userDTO);
		BeanUtils.copyProperties(createdUser, returnValue);		
		return returnValue;
	}
}
