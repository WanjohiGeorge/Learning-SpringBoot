package com.master.demo.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import com.master.demo.shared.dto.AddressDTO;
import com.master.demo.shared.dto.UserDTO;
import com.master.demo.ui.request.models.UserRequestModel;
import com.master.demo.ui.response.models.AddressResponseModel;
import com.master.demo.ui.response.models.OperationStatusModel;
import com.master.demo.ui.response.models.UserResponseModel;
import com.master.demo.ui.service.impl.AddressServiceImpl;
import com.master.demo.ui.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")

public class UserController {
	@Autowired
	UserServiceImpl userService;

	@Autowired
	AddressServiceImpl addressService;

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<UserResponseModel> getUser(@PathVariable String id) {
		ModelMapper mapper = new ModelMapper();
		Link userAddressesLinks= WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddresses(id)).withRel("addresses");
		Link selfLink =  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(id)).withSelfRel();
		
		return EntityModel.of( mapper.map(userService.getUserByUserId(id), UserResponseModel.class), Arrays.asList(selfLink,userAddressesLinks));
	}

	@GetMapping(path = "/{id}/addresses", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public CollectionModel<AddressResponseModel> getUserAddresses(@PathVariable String id) {

		List<AddressResponseModel> response = new ArrayList<>();
		List<AddressDTO> dto = addressService.getAddresses(id);
		if (dto != null && !dto.isEmpty()) {
			java.lang.reflect.Type listType = new TypeToken<List<AddressResponseModel>>() {
			}.getType();
			response = new ModelMapper().map(dto, listType);
			
			for (AddressResponseModel addressR : response) {
				Link selfLink = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddress(id, addressR.getAddressId()))
						.withSelfRel();
				addressR.add(selfLink);
			}
		}

		Link userAddressesLinks= WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddresses(id)).withRel("addresses");
		Link selfLink =  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(id)).withSelfRel();
		return CollectionModel.of(response, userAddressesLinks, selfLink);
	}

	@GetMapping(path = "/{id}/addresses/{addressId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<AddressResponseModel> getUserAddress(@PathVariable String id, @PathVariable String addressId) {

//		using mappings 

		Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("user");
		Link userAddressLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddresses(id))
//				slash(id).
//				slash("user").
//				slash("addresses")
				.withRel("addresses");
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddress(id, addressId))
				
				// .slash(id).slash("user").slash("addresses").slash(addressId).
				.withSelfRel();

		AddressResponseModel r = new ModelMapper().map(addressService.getAddress(addressId),
				AddressResponseModel.class);
//		use EntityModel to return the object with the links.
		return EntityModel.of(r, Arrays.asList(userLink, userAddressLink, selfLink));

	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public UserResponseModel setUser(@RequestBody UserRequestModel userRequestModel) throws Exception {

		if (userRequestModel.getEmail() == null || userRequestModel.getEmail().isEmpty())
			throw new UserServiceExceptions(ErrorMessages.MISSING_REQUIRED_FIELDS.getErrorMessage());

		ModelMapper mapper = new ModelMapper();
		UserDTO userDTO = mapper.map(userRequestModel, UserDTO.class);
		UserDTO createdUser = userService.createUser(userDTO);

		return mapper.map(createdUser, UserResponseModel.class);
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName("DELETE");
		userService.deleteUser(id);
		returnValue.setOperationResult("SUCCESS");
		return returnValue;
	}

//	read request paramenter
	@GetMapping
	public List<UserResponseModel> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {

		List<UserResponseModel> r = new ArrayList<>();
		List<UserDTO> users = userService.getUsers(page, limit);

		for (UserDTO user : users) {
			UserResponseModel ur = new UserResponseModel();
			BeanUtils.copyProperties(user, ur);
			r.add(ur);
		}
		return r;
	}
}
