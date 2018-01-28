package com.ozayduman.socialapp.firends.user.create;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.ozayduman.socialapp.firends.TestContext;
import com.ozayduman.socialapp.firends.TestUtils;
import com.ozayduman.socialapp.firends.user.pagination.UserPaginationFunctionHandler;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateUserFunctionHandlerTest {

	private CreateUserFunctionHandler handler;
	private Context context;

	@Before
	public void init() {
		TestUtils.createUserTable();
        TestUtils.insertDataToUserTable();
		handler = new CreateUserFunctionHandler();
		TestContext context = new TestContext();
		context.setFunctionName("CreateUserFunctionHandler");
	}

	@After
	public void clean() {
		TableUtils.deleteTableIfExists(TestUtils.createDynamoDBClient(), new DeleteTableRequest("User"));
	}
	
    @Test
    public void testCreateUserFunctionHandler() {
        CreateUserRequest request = new  CreateUserRequest();
        request.setUsername("Rose");
        request.setPassword("VeryStrongPassword");
        request.setCity("ANKARA");
        request.setDistrict("ÇANKAYA");
        request.setBirthDate(new Date());
		CreateUserResponse response = handler.handleRequest(request , context);
        Assert.assertNotNull(response);
        assertEquals("OK", response.getStatus());
    }
    
    @Test
    public void WhenCreatingUserWithExistingUserNameThenUserNotCreated() {
        CreateUserRequest request = new  CreateUserRequest();
        request.setUsername("ozay");
        request.setPassword("VeryStrongPassword");
        request.setCity("ANKARA");
        request.setDistrict("ÇANKAYA");
        request.setBirthDate(new Date());
		CreateUserResponse response = handler.handleRequest(request , context);
        Assert.assertNotNull(response);
        assertEquals("ERROR", response.getStatus());
    }
}
