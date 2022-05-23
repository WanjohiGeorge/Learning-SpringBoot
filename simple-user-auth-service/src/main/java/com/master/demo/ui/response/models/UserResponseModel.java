package com.master.demo.ui.response.models;

import java.util.List;

public class UserResponseModel {
	private String userID;
	
	private String username;
	private String firstName;
	private String lastName;	
	private List<AddressResponseModel> addresses;
 
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<AddressResponseModel> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressResponseModel> addresses) {
		this.addresses = addresses;
	}
}
