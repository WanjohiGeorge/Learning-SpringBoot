package com.master.demo.ui.service;

import java.util.List;

import com.master.demo.shared.dto.AddressDTO;

public interface AddressService {
 List<AddressDTO>  getAddresses (String userId);
}
