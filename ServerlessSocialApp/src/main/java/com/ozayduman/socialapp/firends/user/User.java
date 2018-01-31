package com.ozayduman.socialapp.firends.user;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.S3Link;

@DynamoDBTable(tableName = "User")
public class User {

	private String userName;
	private String userId;
	private String passwordHash;
	private String openIdToken;
	private Date birthDate;
	private String mail;
	private String city;
	private String district;
	private S3Link avatar;
	private byte[] avatarInByteArray;

	public User() {
	}

	@DynamoDBHashKey(attributeName = "username")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@DynamoDBAttribute(attributeName = "userid")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@DynamoDBAttribute(attributeName = "passwordhash")
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@DynamoDBAttribute(attributeName = "openidtoken")
	public String getOpenIdToken() {
		return openIdToken;
	}

	public void setOpenIdToken(String openIdToken) {
		this.openIdToken = openIdToken;
	}

	@DynamoDBAttribute(attributeName = "mail")
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@DynamoDBAttribute(attributeName = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@DynamoDBAttribute(attributeName = "district")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@DynamoDBAttribute(attributeName = "birthdate")
	public Date getBirthDate() {
		return birthDate;
	}

	public S3Link getAvatar() {
		return avatar;
	}

	@DynamoDBAttribute(attributeName = "avatar")
	public void setAvatar(S3Link avatar) {
		this.avatar = avatar;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public User withUserName(String username) {
		setUserName(username);
		return this;
	}

	public User withPasswordHash(String passwordHash) {
		setPasswordHash(passwordHash);
		return this;
	}

	public User withUserId(String userId) {
		setUserId(userId);
		return this;
	}

	public User withCity(String city) {
		setCity(city);
		return this;
	}

	public User withDistrict(String district) {
		setDistrict(district);
		return this;
	}

	public User withBirthDate(Date birthDate) {
		setBirthDate(birthDate);
		return this;
	}

	public User withAvatar(S3Link avatar) {
		setAvatar(avatar);
		return this;
	}

	public User withAvatarInByteArray(byte[] avatarInByteArray) {
		setAvatarInByteArray(avatarInByteArray);
		return this;
	}

	public byte[] getAvatarInByteArray() {
		return avatarInByteArray;
	}

	@DynamoDBIgnore
	public void setAvatarInByteArray(byte[] avatarInByteArray) {
		this.avatarInByteArray = avatarInByteArray;
	}

}
