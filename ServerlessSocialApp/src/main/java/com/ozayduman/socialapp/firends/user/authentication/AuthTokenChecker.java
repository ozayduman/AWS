package com.ozayduman.socialapp.firends.user.authentication;

import java.util.Optional;

import com.amazonaws.util.StringUtils;
import com.ozayduman.socialapp.firends.user.AbstractAuthorizedRequestDTO;
import com.ozayduman.socialapp.firends.user.User;
import com.ozayduman.socialapp.firends.user.dao.UserDao;

public class AuthTokenChecker {
	private UserDao userDao;

	public AuthTokenChecker(UserDao userDao) {
		super();
		this.userDao = userDao;
	}
	
	public boolean check(AbstractAuthorizedRequestDTO request) {
		if(isAuthRequestDataValid(request))
		{
			return false;
		}
		Optional<User> user = userDao.findUserByUsername(request.getUsername());
		return user.isPresent() && user.get().getOpenIdToken().equals(request.getToken());
	}

	private boolean isAuthRequestDataValid(AbstractAuthorizedRequestDTO request) {
		return StringUtils.isNullOrEmpty(request.getUsername()) || StringUtils.isNullOrEmpty(request.getToken());
	}

}
