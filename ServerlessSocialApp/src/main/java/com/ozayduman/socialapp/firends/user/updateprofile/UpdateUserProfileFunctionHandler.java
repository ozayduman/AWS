package com.ozayduman.socialapp.firends.user.updateprofile;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ozayduman.socialapp.firends.user.User;
import com.ozayduman.socialapp.firends.user.authentication.AuthTokenChecker;
import com.ozayduman.socialapp.firends.user.authentication.PasswordAuthentication;
import com.ozayduman.socialapp.firends.user.dao.DynamoDBUserDao;

public class UpdateUserProfileFunctionHandler implements RequestHandler<UpdateProfileRequestDTO, UpdateProfileResponseDTO> {

    @Override
    public UpdateProfileResponseDTO handleRequest(UpdateProfileRequestDTO request, Context context) {
    	UpdateProfileResponseDTO response = new UpdateProfileResponseDTO();
    	if(!request.getNewpassword().equals(request.getRenewpassword())) {
    		response.setMessage("password and re password fields mismatch");
    		return response;
    	}
    	
    	AuthTokenChecker tokenChecker = new AuthTokenChecker(DynamoDBUserDao.instance());
		if(tokenChecker.check(request))
		{
			response.setAsUnauthorized();
			return response;
		}
		//TODO : update info and update avatar picture features should be individually implemented
		User user = request.convertToUser();
		PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
		user.withPasswordHash(passwordAuthentication.hash(request.getNewpassword().toCharArray()));
		DynamoDBUserDao.instance().saveOrUpdateUser(user);	
		response.setMessage("OK");
        return response;
    }

}
