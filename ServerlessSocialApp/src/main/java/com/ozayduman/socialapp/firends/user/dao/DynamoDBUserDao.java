package com.ozayduman.socialapp.firends.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.util.StringUtils;
import com.ozayduman.socialapp.firends.user.User;
import com.ozayduman.socialapp.firends.user.pagination.PageUserResponse;

public class DynamoDBUserDao implements UserDao {

	// private static final Logger log = Logger.getLogger(DynamoDBUserDao.class);

	private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

	private static final Integer limit = 5;

	private static volatile DynamoDBUserDao instance;

	public static DynamoDBUserDao instance() {

		if (instance == null) {
			synchronized (DynamoDBUserDao.class) {
				if (instance == null)
					instance = new DynamoDBUserDao();
			}
		}
		return instance;
	}

	@Override
	public List<User> findAllUsers() {
		return mapper.scan(User.class, new DynamoDBScanExpression());
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		User user = mapper.load(User.class, username);
		return Optional.ofNullable(user);
	}

	@Override
	public void saveOrUpdateUser(User user) {
		mapper.save(user);
	}

	@Override
	public void deleteUser(String username) {
		Optional<User> user = findUserByUsername(username);
		if (user.isPresent()) {
			mapper.delete(user.get());
		} else {
			// log.error("Could not delete event, no such team and date combination");
			throw new IllegalArgumentException("Delete failed for nonexistent user");
		}
	}

	@Override
	public ScanResultPage<User> getAllUsersWithPages(String lastEvaluatedKey) {
		final DynamoDBScanExpression scanPageExpression = new DynamoDBScanExpression().withLimit(limit);
		if (!StringUtils.isNullOrEmpty(lastEvaluatedKey)) {
			Map<String, AttributeValue> lastKey = new HashMap<>();
			lastKey.put("username", new AttributeValue().withS(lastEvaluatedKey));
			scanPageExpression.setExclusiveStartKey(lastKey);
		}
		ScanResultPage<User> scanPage = mapper.scanPage(User.class, scanPageExpression);
		return scanPage;
	}

}
