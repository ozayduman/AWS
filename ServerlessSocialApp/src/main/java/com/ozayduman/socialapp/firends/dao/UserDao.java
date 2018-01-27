package com.ozayduman.socialapp.firends.dao;

import java.util.List;
import java.util.Optional;

import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.ozayduman.socialapp.firends.user.User;

public interface UserDao {
	List<User> findAllUsers();
	Optional<User> findUserByUsername(String username);
	void saveOrUpdateUser(User user);
	void deleteUser(String username);
	ScanResultPage<User> getAllUsersWithPages(String lastEvaluatedKey);
}
