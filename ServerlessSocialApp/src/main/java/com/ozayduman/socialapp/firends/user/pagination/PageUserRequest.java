package com.ozayduman.socialapp.firends.user.pagination;

import com.ozayduman.socialapp.firends.user.AbstractAuthorizedRequestDTO;

public class PageUserRequest extends AbstractAuthorizedRequestDTO{
	private String lastEvaluatedKey;

	public PageUserRequest() {
	}

	public String getLastEvaluatedKey() {
		return lastEvaluatedKey;
	}

	public void setLastEvaluatedKey(String lastEvaluatedKey) {
		this.lastEvaluatedKey = lastEvaluatedKey;
	}
	
}
