package com.ozayduman.socialapp.firends.user.create;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ozayduman.socialapp.firends.user.User;
import com.ozayduman.socialapp.firends.user.authentication.PasswordAuthentication;
import com.ozayduman.socialapp.firends.user.dao.DynamoDBUserDao;

public class CreateUserFunctionHandler implements RequestHandler<CreateUserRequest, CreateUserResponse> {

	@Override
	public CreateUserResponse handleRequest(CreateUserRequest request, Context context) {
		CreateUserResponse response = new CreateUserResponse();
		if (checkUserNameAvaliability(request.getUsername())) {
			User user = request.convertToUser();
			PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
			user.withPasswordHash(passwordAuthentication.hash(request.getPassword().toCharArray()));
			DynamoDBUserDao.instance().saveOrUpdateUser(user);	
			response.setStatus("OK");
		}else {
			response.setStatus("ERROR");
			response.setMessage("username is not avaliable");
		}
		return response;
	}

	private boolean checkUserNameAvaliability(String username) {
		return !DynamoDBUserDao.instance()
				.findUserByUsername(username)
				.isPresent();
	}

}
