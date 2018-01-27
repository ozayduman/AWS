package com.ozayduman.socialapp.firends.user;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "User")
public class User {

	private String userName;
	private Integer userId;
	private String passwordHash;
	private String openIdToken;
	private Date birthDate;
	private String mail;
	private String city;
	private String district;

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
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
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

	public User withUserId(Integer userId) {
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
}
