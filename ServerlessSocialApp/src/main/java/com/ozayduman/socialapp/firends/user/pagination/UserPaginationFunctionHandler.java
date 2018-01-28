package com.ozayduman.socialapp.firends.user.pagination;

import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ozayduman.socialapp.firends.user.User;
import com.ozayduman.socialapp.firends.user.dao.DynamoDBUserDao;

public class UserPaginationFunctionHandler implements RequestHandler<PageUserRequest, PageUserResponse> {

	@Override
	public PageUserResponse handleRequest(PageUserRequest request, Context context) {
		// context.getLogger().log("Input: " + request);
		PageUserResponse response = new PageUserResponse();
		ScanResultPage<User> scanResultPage = DynamoDBUserDao.instance()
				.getAllUsersWithPages(request.getLastEvaluatedKey());
		scanResultPage.getResults().forEach(u -> {
			response.addUserDTO(new UserDTO(u));
		});
		response.setLastEvaluatedKey(getLastEvaluatedKey(scanResultPage));
		return response;
	}

	private String getLastEvaluatedKey(ScanResultPage<User> scanResultPage) {
		if(scanResultPage.getLastEvaluatedKey()!=null && scanResultPage.getLastEvaluatedKey().get("username")!=null) {
			return scanResultPage.getLastEvaluatedKey().get("username").getS();
		}
		return null;
	}

}
