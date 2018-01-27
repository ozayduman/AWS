package com.ozayduman.socialapp.firends.user.authentication;

public class AuthenticateUserRequest {
	private String username;
	private String passwordHash;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
