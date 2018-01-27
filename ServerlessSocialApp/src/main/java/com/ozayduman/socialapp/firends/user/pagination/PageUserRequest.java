package com.ozayduman.socialapp.firends.user.pagination;

public class PageUserRequest {
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
