package com.ozayduman.socialapp.firends.user.messaging;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Message")
public class Message {
	private String recipient;
	private Date date;
	private String sender;
	private String message;

	@DynamoDBHashKey(attributeName = "recipient")
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	@DynamoDBRangeKey
	@DynamoDBAttribute(attributeName = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@DynamoDBAttribute(attributeName = "sender")
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@DynamoDBAttribute(attributeName = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Message withRecipient(String recipient) {
		setRecipient(recipient);
		return this;
	}

	public Message withDate(Date date) {
		setDate(date);
		return this;
	}

	public Message withSender(String sender) {
		setSender(sender);
		return this;
	}
	
	public Message withMessage(String message) {
		setMessage(message);
		return this;
	}

}
