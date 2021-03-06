package com.ozayduman.socialapp.firends.user.dao;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;


public class DynamoDBManager {

    private static volatile DynamoDBManager instance;

    private static DynamoDBMapper mapper;

    private DynamoDBManager() {
    	/*AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
				new AwsClientBuilder.EndpointConfiguration("http://localhost:8000/", "us-west-2")).build();*/
    	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
    			.build();
    	
    	AWSCredentialsProvider awsCredentialsProvider = new DefaultAWSCredentialsProviderChain(); 
		mapper = new DynamoDBMapper(client,awsCredentialsProvider);
    }

    public static DynamoDBManager instance() {

        if (instance == null) {
            synchronized(DynamoDBManager.class) {
                if (instance == null)
                    instance = new DynamoDBManager();
            }
        }

        return instance;
    }

    public static DynamoDBMapper mapper() {
        DynamoDBManager manager = instance();
        return manager.mapper;
    }

}
