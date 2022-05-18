package com.master.demo.ui.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.master.demo.exceptions.ErrorMessages;
import com.master.demo.exceptions.UserServiceExceptions;
import com.master.demo.shared.dto.UserDTO;
import com.master.demo.ui.request.models.UserRequestModel;
import com.master.demo.ui.response.models.UserResponseModel;
import com.master.demo.ui.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")

public class UserController {
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping(path="/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserResponseModel getUser(@PathVariable String id) {		
		UserResponseModel returnValue =new UserResponseModel();
		
		UserDTO userDto = userService.getUserByUserId(id);
	
		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}
	
	@PostMapping(consumes={ MediaType.APPLICATION_JSON_VALUE})
	public UserResponseModel setUser(@RequestBody UserRequestModel userRequestModel) throws Exception {		
		UserResponseModel returnValue =new UserResponseModel();
		
		if (userRequestModel.getEmail()==null || userRequestModel.getEmail().isEmpty() ) 
			throw new UserServiceExceptions (ErrorMessages.MISSING_REQUIRED_FIELDS.getErrorMessage());
		UserDTO userDTO =  new UserDTO();
		BeanUtils.copyProperties(userRequestModel, userDTO);
		
		UserDTO createdUser = userService.createUser(userDTO);
		BeanUtils.copyProperties(createdUser, returnValue);		
		return returnValue;
	}
}
