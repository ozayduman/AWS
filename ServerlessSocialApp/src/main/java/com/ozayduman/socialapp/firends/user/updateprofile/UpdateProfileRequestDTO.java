package com.ozayduman.socialapp.firends.user.updateprofile;

import java.util.Date;

import com.ozayduman.socialapp.firends.user.AbstractAuthorizedRequestDTO;
import com.ozayduman.socialapp.firends.user.User;

public class UpdateProfileRequestDTO extends AbstractAuthorizedRequestDTO {
	private String oldpassword;
	private String newpassword;
	private String renewpassword;
	private Date birthDate;
	private String city;
	private String district;
	private String avatarEncodedString;
	
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getRenewpassword() {
		return renewpassword;
	}
	public void setRenewpassword(String renewpassword) {
		this.renewpassword = renewpassword;
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
	public String getAvatarEncodedString() {
		return avatarEncodedString;
	}
	public void setAvatarEncodedString(String avatarEncodedString) {
		this.avatarEncodedString = avatarEncodedString;
	}
	public User convertToUser() {
		return new User()
		.withPasswordHash(newpassword)
		.withBirthDate(birthDate)
		.withCity(city)
		.withDistrict(district)
		.withAvatarInByteArray(avatarEncodedString);
	}
	
}
