package com.ozayduman.socialapp.firends.user.pagination;

import java.util.Date;

import com.ozayduman.socialapp.firends.user.User;

public class UserDTO {
	private String userName;
	private String userId;
	private Date birthDate;
	private String city;
	private String district;
	
	
	public UserDTO() {
	}
	
	public UserDTO(User user) {
		this.userName = user.getUserName();
		this.userId = user.getUserId();
		this.birthDate = user.getBirthDate();
		this.city = user.getCity();
		this.district = user.getDistrict();
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
