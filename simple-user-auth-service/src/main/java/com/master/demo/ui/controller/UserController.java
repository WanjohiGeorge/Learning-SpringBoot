package com.master.demo.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.master.demo.exceptions.ErrorMessages;
import com.master.demo.exceptions.UserServiceExceptions;
import com.master.demo.shared.dto.UserDTO;
import com.master.demo.ui.request.models.UserRequestModel;
import com.master.demo.ui.response.models.OperationStatusModel;
import com.master.demo.ui.response.models.UserResponseModel;
import com.master.demo.ui.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")

public class UserController {
	@Autowired
	UserServiceImpl userService;

//	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserResponseModel getUser(@PathVariable String id) {
		UserResponseModel returnValue = new UserResponseModel();

		UserDTO userDto = userService.getUserByUserId(id);

		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public UserResponseModel setUser(@RequestBody UserRequestModel userRequestModel) throws Exception {
		UserResponseModel returnValue = new UserResponseModel();

		if (userRequestModel.getEmail() == null || userRequestModel.getEmail().isEmpty())
			throw new UserServiceExceptions(ErrorMessages.MISSING_REQUIRED_FIELDS.getErrorMessage());
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userRequestModel, userDTO);

		UserDTO createdUser = userService.createUser(userDTO);
		BeanUtils.copyProperties(createdUser, returnValue);
		return returnValue;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName("DEETE");
		userService.deleteUser(id);
		returnValue.setOperationResult("SUCCESS");
		return returnValue;
	}

//	read request paramenter
	@GetMapping
	public List<UserResponseModel> getUsers(@RequestParam(value = "page",defaultValue = "0") int page, 
			@RequestParam(value = "limit", defaultValue="25") int limit) {

		List<UserResponseModel> r = new ArrayList<>();
		List<UserDTO> users = userService.getUsers(page, limit);
		
		for(UserDTO user: users) {
			UserResponseModel ur =  new UserResponseModel();
			BeanUtils.copyProperties(user, ur);
			r.add(ur);
		}
		return r;
	}
}
