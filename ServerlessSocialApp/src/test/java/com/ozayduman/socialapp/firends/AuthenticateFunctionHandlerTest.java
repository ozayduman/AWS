package com.ozayduman.socialapp.firends;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.ozayduman.socialapp.firends.user.User;
import com.ozayduman.socialapp.firends.user.authentication.AuthenticateFunctionHandler;
import com.ozayduman.socialapp.firends.user.authentication.AuthenticateUserRequest;
import com.ozayduman.socialapp.firends.user.authentication.AuthenticateUserResponse;
import com.ozayduman.socialapp.firends.user.dao.DynamoDBManager;

public class AuthenticateFunctionHandlerTest {

	private AuthenticateFunctionHandler handler;
	private Context context;

	@Before
	public void init() {
		createUserTable();
		insertDataToUserTable();
		handler = new AuthenticateFunctionHandler();
		TestContext context = new TestContext();
		context.setFunctionName("UserAuthenticationFunctionHandler");
	}

	@After
	public void clean() {
		TableUtils.deleteTableIfExists(createDynamoDBClient(), new DeleteTableRequest("User"));
	}

	private AmazonDynamoDB createDynamoDBClient() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
				new AwsClientBuilder.EndpointConfiguration("http://localhost:8000/", "us-west-2")).build();
		return client;
	}

	private void createUserTable() {
		AmazonDynamoDB client = createDynamoDBClient();
		CreateTableRequest createTableRequest = new CreateTableRequest().withTableName("User");
		createTableRequest
				.withKeySchema(new KeySchemaElement().withAttributeName("username").withKeyType(KeyType.HASH))
				.withAttributeDefinitions(Arrays.asList(new AttributeDefinition("username",ScalarAttributeType.S)));
		
		createTableRequest.setProvisionedThroughput(
				new ProvisionedThroughput().withReadCapacityUnits(5L).withWriteCapacityUnits(2L));
		TableUtils.createTableIfNotExists(client, createTableRequest);
	
	}

	private void insertDataToUserTable() {
		DynamoDBMapper mapper = DynamoDBManager.mapper();
		User user = new User().withUserName("ozay").withPasswordHash("123").withUserId(1).withCity("Ankara")
				.withDistrict("Çankaya");
		mapper.save(user);
	}


	@Test
	public void WhenTrueCredentialsGivenAuthenticatesSuccesfully() {
		AuthenticateUserRequest request = new AuthenticateUserRequest();
		request.setUsername("ozay");
		request.setPasswordHash("123");
		AuthenticateUserResponse response = handler.handleRequest(request, context);
		assertNotNull(response.getUserId());
		assertNotNull(response.getOpenIdToken());
		assertTrue(Boolean.valueOf(response.getStatus()));
	}

	@Test
	public void WhenFalseCredentialsGivenAuthenticationFails() {
		AuthenticateUserRequest request = new AuthenticateUserRequest();
		request.setUsername("notexistinguser");
		request.setPasswordHash("***");
		AuthenticateUserResponse response = handler.handleRequest(request, context);
		assertNull(response.getUserId());
		assertNull(response.getOpenIdToken());
		assertFalse(Boolean.valueOf(response.getStatus()));
	}
}
