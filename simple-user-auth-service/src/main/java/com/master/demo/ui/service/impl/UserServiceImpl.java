package com.master.demo.ui.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.master.demo.io.entities.UserEntity;
import com.master.demo.io.repositories.UserRepository;
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
		UserEntity userEntity = new UserEntity();

		BeanUtils.copyProperties(user, userEntity);

//		check if user exists before trying to save then again
		UserEntity userXEntity = userRepository.findByEmail(user.getEmail());
		if (userXEntity != null)
			throw new RuntimeException("User with email " + user.getEmail() + " already Exists");

//		generate a new UserID;
		userEntity.setUserID(utils.GenerateUserID());
		userEntity.setEncPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		UserEntity savedUser = userRepository.save(userEntity);

		UserDTO returnV = new UserDTO();
		BeanUtils.copyProperties(savedUser, returnV);

		return returnV;
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
	public UserDTO getUSer(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		UserDTO userDTO = new UserDTO();
		if (userEntity != null)
			BeanUtils.copyProperties(userEntity, userDTO);
		
		return userDTO;
	}

	@Override
	public UserDTO getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserID(userId);
		UserDTO userDTO = new UserDTO();
		if (userEntity != null)
			BeanUtils.copyProperties(userEntity, userDTO);
		
		return userDTO;	
	}

}
