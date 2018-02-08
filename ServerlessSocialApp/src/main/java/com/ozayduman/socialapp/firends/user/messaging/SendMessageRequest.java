package com.ozayduman.socialapp.firends.user.messaging;

import java.util.Date;

import com.ozayduman.socialapp.firends.user.AbstractAuthorizedRequestDTO;

public class SendMessageRequest extends AbstractAuthorizedRequestDTO{

	private String recipient;
	private String sender;
	private String message;

	public Message convertToMessage() {
		return new Message()
				.withDate(new Date())
				.withRecipient(recipient)
				.withSender(sender)
				.withMessage(message);
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
