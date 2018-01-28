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
		TestUtils.createUserTable();
        TestUtils.insertDataToUserTable();
		handler = new AuthenticateFunctionHandler();
		TestContext context = new TestContext();
		context.setFunctionName("UserAuthenticationFunctionHandler");
	}

	@After
	public void clean() {
		TableUtils.deleteTableIfExists(TestUtils.createDynamoDBClient(), new DeleteTableRequest("User"));
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
