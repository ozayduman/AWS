# Serverless Social App using AWS Lambda & DynamoDB

This application banckend was implemented with a server-less architecture, using [AWS Lambda](http://aws.amazon.com/lambda/) to host and execute the code and [Amazon DynamoDB](http://aws.amazon.com/dynamodb/) as persistent storage. This provides a cost-efficient solution that is scalable and highly available.
The basic functions implemented are in ServerlessSocialApp as follows:
+ new user creation
+ login, getting back an authentication "token"
+ list users profiles with pagination
- password change
- password reset, an email is sent with a link to reset the password
For details of each lambda function take a look at unit and integration test
## APIs

The APIs are exposed as AWS Lambda Functions:

| Function                      | Input                         | Output                                 |
|-------------------------------|-------------------------------|----------------------------------------|
|CreateUserFunctionHandler      |email, password                | created: true / false                  |
|AuthenticateFunctionHandler    |email, verify                  | verified: true / false                 |
|UserPaginationFunctionHandler  |email, lost, password          | changed: true / false                  |
|LambdAuthLogin(-)              |email, password                | login: true / false,	identityId, token|
|LambdAuthChangePassword(-)     |email, oldPassword, newPassword | changed: true / false                 |
|LambdAuthLostPassword(-)       |email                          | sent: true / false                     |
|LambdAuthResetPassword(-)      |email, lost, password          | changed: true / false                  |
