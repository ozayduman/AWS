package com.ozayduman.socialapp.firends.user;

public abstract class AbstractResponseDTO {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setAsUnauthorized() {
		setMessage("Unauthorized");
	}

}
