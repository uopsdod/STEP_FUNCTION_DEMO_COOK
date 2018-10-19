package com.demo.activity;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.GetActivityTaskRequest;
import com.amazonaws.services.stepfunctions.model.GetActivityTaskResult;
import com.amazonaws.services.stepfunctions.model.SendTaskFailureRequest;
import com.amazonaws.services.stepfunctions.model.SendTaskSuccessRequest;
import com.google.gson.Gson;

public class Cook {
	
	String activity_cook_agn;
	AWSStepFunctions client;
	
	public Cook() {
		int timeout = 5000;
		
		/** get aws credential profile **/
    	String credential_profile = "stsai";

		/** get an AWS Setp function client **/
    	ClientConfiguration withRequestTimeout = new ClientConfiguration().withClientExecutionTimeout(timeout);
    	this.client = AWSStepFunctionsClientBuilder.standard().withCredentials(new ProfileCredentialsProvider(credential_profile)).withClientConfiguration(withRequestTimeout).build();
		
    	/** get activity arn **/
    	this.activity_cook_agn = "arn:aws:states:us-east-1:602307824922:activity:Cook";		
	}
	
	public static void main( String[] args )
    {

		/** poll for task to work on **/
    	Cook cook = new Cook();
    	while(true) {
    		try {
    			cook.execute();
    		}catch(Exception e) {
    			System.out.println("simplified error msg: " + e.getMessage());
    			// e.printStackTrace();
    		}
    	}
    	
    }
	
	public void execute() throws Exception{
		System.out.println("looking for work to do ...");
		GetActivityTaskResult activityTask = this.client.getActivityTask(new GetActivityTaskRequest().withActivityArn(this.activity_cook_agn).withWorkerName("Chef001"));
		System.out.println("found work! - taskToken: " + activityTask.getTaskToken());
		System.out.println("found work! - input: " + activityTask.getInput());
		Gson gson = new Gson();
		com.demo.activity.CookInput cookInput = gson.fromJson(activityTask.getInput(), com.demo.activity.CookInput.class);
		
		boolean workOK = true;
		if (workOK) {
			client.sendTaskSuccess(new SendTaskSuccessRequest().withTaskToken(activityTask.getTaskToken()).withOutput(gson.toJson(this.getResult(cookInput))));
		}else {
			client.sendTaskFailure(new SendTaskFailureRequest().withTaskToken(activityTask.getTaskToken()));
		}		
	}
	
    public CookOutput getResult(com.demo.activity.CookInput cookInput) {
    	int workingHourInSec = cookInput.getOrderNumber() * 10;
    	return new CookOutput(workingHourInSec, cookInput.getOrderNumber());
    }
}
