package com.ozayduman.socialapp.firends.user.pagination;

import java.util.ArrayList;
import java.util.List;

import com.ozayduman.socialapp.firends.user.AbstractResponseDTO;

public class PageUserResponse extends AbstractResponseDTO{
	private String lastEvaluatedKey;
	private List<UserDTO> userDTOs;
	
	public PageUserResponse() {
		userDTOs = new ArrayList<>();
	}
	
	public void addUserDTO(UserDTO userDTO) {
		userDTOs.add(userDTO);
	}
	
	public String getLastEvaluatedKey() {
		return lastEvaluatedKey;
	}
	public void setLastEvaluatedKey(String lastEvaluatedKey) {
		this.lastEvaluatedKey = lastEvaluatedKey;
	}
	public List<UserDTO> getUserDTOs() {
		return userDTOs;
	}
	public void setUserDTOs(List<UserDTO> userDTOs) {
		this.userDTOs = userDTOs;
	}
}
