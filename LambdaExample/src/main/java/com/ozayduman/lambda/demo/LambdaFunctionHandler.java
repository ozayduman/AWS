package com.ozayduman.lambda.demo;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class LambdaFunctionHandler implements RequestHandler<PersonRequest, PersonResponse> {
	private DynamoDB dynamoDb;
	private String DYNAMODB_TABLE_NAME = "Person";

	public PersonResponse handleRequest(PersonRequest personRequest, Context context) {
		context.getLogger().log("Input: " + personRequest);
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();		
		this.dynamoDb = new DynamoDB(client);
		personRequest.setId(UUID.randomUUID().toString());
		persistData(personRequest);
		publishToTopic(personRequest.getId());
		PersonResponse personResponse = new PersonResponse();
		personResponse.setMessage("Saved Successfully!!!");
		return personResponse;
	}

	private PutItemOutcome persistData(PersonRequest personRequest) throws ConditionalCheckFailedException {
		return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
				.putItem(new PutItemSpec().withItem(new Item()
						.withPrimaryKey("id",UUID.randomUUID().toString())
						.withString("firstName", personRequest.getFirstName())
						.withString("lastName", personRequest.getLastName())));
	}
	
	private void publishToTopic(String message) {
		AmazonSNS sns = AmazonSNSClientBuilder.standard().build();  
		PublishRequest publishRequest = new PublishRequest("arn:aws:sns:us-east-1:314075330541:ozay", message);
		PublishResult publishResult = sns.publish(publishRequest);
		//print MessageId of message published to SNS topic
		System.out.println("MessageId - " + publishResult.getMessageId());
	}

}
