package com.master.demo.ui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.master.demo.io.entities.AddressEntity;
import com.master.demo.io.entities.UserEntity;
import com.master.demo.io.repositories.AddressRepository;
import com.master.demo.io.repositories.UserRepository;
import com.master.demo.shared.dto.AddressDTO;
import com.master.demo.ui.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	AddressRepository addressRepository;

	@Override
	public List<AddressDTO> getAddresses(String userId) {
//		get User

		UserEntity userEntity = userRepository.findByUserID(userId);
		if (userEntity == null)
			return null;
		Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
		List<AddressDTO> add = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		for (AddressEntity addressEntity : addresses) {
			add.add(mapper.map(addressEntity, AddressDTO.class));
		}

		return add;
	}

	public AddressDTO getAddress(String addressId) {
		return new ModelMapper().map(addressRepository.findByAddressId(addressId), AddressDTO.class);
	}

}
