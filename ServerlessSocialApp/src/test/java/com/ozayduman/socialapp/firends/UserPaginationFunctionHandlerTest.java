package com.ozayduman.socialapp.firends;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.StringUtils;
import com.ozayduman.socialapp.firends.dao.DynamoDBManager;
import com.ozayduman.socialapp.firends.user.User;
import com.ozayduman.socialapp.firends.user.pagination.PageUserRequest;
import com.ozayduman.socialapp.firends.user.pagination.PageUserResponse;
import com.ozayduman.socialapp.firends.user.pagination.UserPaginationFunctionHandler;

public class UserPaginationFunctionHandlerTest {

	private UserPaginationFunctionHandler handler;
	private Context context;

	@Before
	public void init() {
		createUserTable();
		insertDataToUserTable();
		handler = new UserPaginationFunctionHandler();
		TestContext context = new TestContext();
		context.setFunctionName("UserPaginationFunctionHandlerTest");
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
		createTableRequest.withKeySchema(new KeySchemaElement().withAttributeName("username").withKeyType(KeyType.HASH))
				.withAttributeDefinitions(Arrays.asList(new AttributeDefinition("username", ScalarAttributeType.S)));

		createTableRequest.setProvisionedThroughput(
				new ProvisionedThroughput().withReadCapacityUnits(5L).withWriteCapacityUnits(2L));
		TableUtils.createTableIfNotExists(client, createTableRequest);
	}

	private void insertDataToUserTable() {
		DynamoDBMapper mapper = DynamoDBManager.mapper();
		IntStream.range(0, 100).forEach(i -> {
			System.out.println(i);
			User user = new User().withUserName("ozay" + "-" + i).withPasswordHash("123" + "-" + i).withUserId(i)
					.withCity("Ankara").withDistrict("Çankaya");
			mapper.save(user);
		});
	}
	
	@Test
	public void WhenInitialUserPageRequestThenReturnLastEvaluatedKey() {
		PageUserRequest request = new PageUserRequest();
		request.setLastEvaluatedKey(null);
		PageUserResponse response = handler.handleRequest(request , context);
		assertEquals(5, response.getUserDTOs().size());
		assertFalse(StringUtils.isNullOrEmpty(response.getLastEvaluatedKey()));
	}
	
	@Test
	public void WhenSubSequentialUserPageRequestThenSuccessful() {
		PageUserRequest request = new PageUserRequest();
		request.setLastEvaluatedKey(null);
		PageUserResponse response = handler.handleRequest(request , context);
		assertEquals(response.getUserDTOs().get(4).getUserName(), response.getLastEvaluatedKey());
		
		request.setLastEvaluatedKey(response.getLastEvaluatedKey());
		PageUserResponse response2 = handler.handleRequest(request, context);
		assertEquals(response2.getUserDTOs().get(4).getUserName(), response2.getLastEvaluatedKey());
		
		request.setLastEvaluatedKey(response2.getLastEvaluatedKey());
		PageUserResponse response3 = handler.handleRequest(request, context);
		assertEquals(response3.getUserDTOs().get(4).getUserName(), response3.getLastEvaluatedKey());
	}

}
