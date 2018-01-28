package com.ozayduman.socialapp.firends.user.create;

import java.util.Date;
import java.util.UUID;

import com.ozayduman.socialapp.firends.user.User;

public class CreateUserRequest {

	private String username;
	private String password;
	private Date birthDate;
	private String city;
	private String district;

	public User convertToUser() {
		return new User()
		.withUserName(username)
		.withBirthDate(birthDate)
		.withUserId(UUID.randomUUID().toString())
		.withCity(city)
		.withDistrict(district);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

}
