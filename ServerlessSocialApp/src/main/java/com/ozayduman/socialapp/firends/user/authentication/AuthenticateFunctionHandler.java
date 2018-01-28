package com.ozayduman.socialapp.firends.user.authentication;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ozayduman.socialapp.firends.user.User;
import com.ozayduman.socialapp.firends.user.dao.DynamoDBManager;

public class AuthenticateFunctionHandler implements RequestHandler<AuthenticateUserRequest, AuthenticateUserResponse> {
	
	private static DynamoDBMapper mapper;

	@Override
	public AuthenticateUserResponse handleRequest(AuthenticateUserRequest authenticateUserRequest, Context context) {
		return authenticate(authenticateUserRequest);
	}

	private AuthenticateUserResponse authenticate(AuthenticateUserRequest request) {
		AuthenticateUserResponse authenticateUserResponse = new AuthenticateUserResponse();

		User user = authenticateUser(request);
		if (user != null) {
			authenticateUserResponse.setUserId(user.getUserId());
			authenticateUserResponse.setStatus("true");
			authenticateUserResponse.setOpenIdToken(user.getOpenIdToken());
		} else {
			authenticateUserResponse.setUserId(null);
			authenticateUserResponse.setStatus("false");
			authenticateUserResponse.setOpenIdToken(null);
		}

		return authenticateUserResponse;
	}

	public User authenticateUser(AuthenticateUserRequest request) {
		User user = null;

		mapper = createDynamoDBMapper();

		user = mapper.load(User.class, request.getUsername());

		if (user != null) {
			if (user.getPasswordHash().equalsIgnoreCase(request.getPasswordHash())) {
				String openIdToken = getOpenIdToken(user);
				user.setOpenIdToken(openIdToken);
				return user;
			}
		}

		return user;
	}

	private String getOpenIdToken(User user) {
		String token = UUID.randomUUID().toString();
		user.setOpenIdToken(token);
		mapper.save(user);
		return token;
	}

	protected DynamoDBMapper createDynamoDBMapper() {
		return DynamoDBManager.mapper();
	}
}
