package com.george.app.ws.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.george.app.ws.io.entity.UserEntity;
import com.george.app.ws.io.repositories.UserRepository;
import com.george.app.ws.service.UserService;
import com.george.app.ws.shared.dto.UserDto;
import com.george.app.ws.shared.dto.Utils;

@Service
public class UserServiceImpl implements UserService {

//	auto wire Repository to this class
	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto user) {

		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new RuntimeException("Record already Exists");
		
		UserEntity userEntity = new UserEntity();

		BeanUtils.copyProperties(user, userEntity);

		
//		encode password
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(utils.generateUserId(50));

//		use the autowired class to
		UserEntity storedDetails = userRepository.save(userEntity);
		UserDto returnValue = new UserDto();

		BeanUtils.copyProperties(storedDetails, returnValue);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		use find byEmail
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) throw new UsernameNotFoundException(email);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity =  userRepository.findByEmail(email);
		if (userEntity == null) 
			throw new UsernameNotFoundException(email);
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity,returnValue);
		return returnValue;
	}

}
