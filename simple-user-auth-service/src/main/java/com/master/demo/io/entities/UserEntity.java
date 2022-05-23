package com.master.demo.io.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name = "user1")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -1685776170811219919L;

	@javax.persistence.Id
	@GeneratedValue
	private long Id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false, unique = true)
	private String email;	
	
	@Column(nullable = false, unique = true)
	private String userID;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String encPassword;
	
//	user owns the addresses. MapBy i.e. The part where to the other entity knows this class
//	cascade to persist all changes made to UserEntity to child
	@OneToMany(mappedBy ="userDetails", cascade = CascadeType.ALL)
	private  List<AddressEntity> addresses;

	public String getEncPassword() {
		return encPassword;
	}

	public void setEncPassword(String encPassword) {
		this.encPassword = encPassword;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<AddressEntity> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressEntity> addresses) {
		this.addresses = addresses;
	}

}
