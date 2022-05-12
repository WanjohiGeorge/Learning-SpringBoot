package com.george.app.ws.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.george.app.ws.service.UserService;
import com.george.app.ws.shared.dto.UserDto;
import com.george.app.ws.ui.model.request.UserDetailsRequestModel;
import com.george.app.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserController {
	/* define the userService autowire it */
	@Autowired
	UserService userService;

	// bind the method to a http GET request so as to be called

	@GetMapping
	public String getUser() {
		return "Get User has been called";
	}

	@PostMapping
//	to be able to read the body from http request +JavaClass to create a UserDetailsRequestModel Object from this body 
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {

		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
		
		BeanUtils.copyProperties(userDetails, userDto); // copy the properties between the classes

		UserDto createdUser = userService.createUser(userDto);

		BeanUtils.copyProperties(createdUser, returnValue);
		return returnValue;
	}

	@PutMapping
	public String updateUser() {
		return "Update User has been called";
	}

	@DeleteMapping
	public String deleteUser() {
		return "Delete user has been called";
	}

}
