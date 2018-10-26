STEPS for using the project:

STEP01 - Create two Roles:
One for account to have permissions to use Step Function and Lambda function
One for Lambda to have permissions to call another Lambda function 

STEP02 - create Lambda functions:
upload each Lambda RequestHandler class to AWS and get their arns

STEP03 - create State Machines:
use the arns of those Lambda functions and then use classes under package com.demo to create them 

STEP04 - test: 
Go to AWS Step function console and test your flow


Notes For development: 
TODO:
* use Docker to batch upload lambda functions

