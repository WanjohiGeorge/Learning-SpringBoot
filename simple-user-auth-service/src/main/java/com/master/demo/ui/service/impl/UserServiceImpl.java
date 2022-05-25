package com.master.demo.ui.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.master.demo.exceptions.UserServiceExceptions;
import com.master.demo.io.entities.UserEntity;
import com.master.demo.io.repositories.UserRepository;
import com.master.demo.shared.dto.AddressDTO;
import com.master.demo.shared.dto.UserDTO;
import com.master.demo.ui.service.UserService;
import com.master.demo.utils.Utilities;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	Utilities utils;

	@Override
	public UserDTO createUser(UserDTO user) {

//		check if user exists before trying to save then again

		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new RuntimeException("User with email " + user.getEmail() + " already Exists");

//		update addresses with Id

		for (int i = 0; i < user.getAddresses().size(); i++) {
			AddressDTO address = user.getAddresses().get(i);
			address.setAddressId(utils.generateId(30));
			address.setUserDetails(user);
			user.getAddresses().set(i, address);
		}
		ModelMapper mapper = new ModelMapper();
		UserEntity userEntity = mapper.map(user, UserEntity.class);

//		generate a new UserID;
		userEntity.setUserID(utils.generateId(50));
		userEntity.setEncPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		UserEntity savedUser = userRepository.save(userEntity);
		UserDTO r = mapper.map(savedUser, UserDTO.class);
		return r;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);
		if (userEntity == null)
			throw new UsernameNotFoundException(username);
		else
			return new User(userEntity.getEmail(), userEntity.getEncPassword(), new ArrayList<>());
	}

	@Override
	public UserDTO getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		UserDTO userDTO = new UserDTO();
		if (userEntity == null)
			throw new UsernameNotFoundException(email); else
			BeanUtils.copyProperties(userEntity, userDTO);

		return userDTO;
	}

	@Override
	public UserDTO getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserID(userId);

		ModelMapper mapper = new ModelMapper();

		if (userEntity == null)
			throw new UsernameNotFoundException(userId);
		else return mapper.map(userEntity, UserDTO.class);
		
	}

	@Override
	public void deleteUser(String userID) {
		UserEntity userEntity = userRepository.findByUserID(userID);
		if (userEntity == null)
			throw new UserServiceExceptions("User not found");

		userRepository.delete(userEntity);
	}

	@Override
	public List<UserDTO> getUsers(int page, int limit) {
		if (page > 0)
			page -= 1;

		List<UserDTO> returnValue = new ArrayList<>();
		// create a pageable request that returns pages
		Pageable pageableRequest = PageRequest.of(page, limit);
//		get pages returned by pageable request 
		Page<UserEntity> userPages = userRepository.findAll(pageableRequest);
		// get a list of entities from the pages
		List<UserEntity> users = userPages.getContent();

		for (UserEntity user : users) {
			UserDTO uDTO = new UserDTO();
			BeanUtils.copyProperties(user, uDTO);
			returnValue.add(uDTO);
		}

		return returnValue;
	}

}
