package com.demo.activity.cook;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
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
		int timeout = 10000;
		
		/** get an AWS Setp function client **/
    	ClientConfiguration withRequestTimeout = new ClientConfiguration().withClientExecutionTimeout(timeout);
    	this.client = AWSStepFunctionsClientBuilder.standard().withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).withClientConfiguration(withRequestTimeout).build();
		
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
    			System.out.println("work not found or failed: " + e.getMessage());
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
		com.demo.activity.cook.CookInput cookInput = gson.fromJson(activityTask.getInput(), com.demo.activity.cook.CookInput.class);
		
		boolean workOK = true;
		if (workOK) {
			client.sendTaskSuccess(new SendTaskSuccessRequest().withTaskToken(activityTask.getTaskToken()).withOutput(gson.toJson(this.getResult(cookInput))));
		}else {
			client.sendTaskFailure(new SendTaskFailureRequest().withTaskToken(activityTask.getTaskToken()));
		}		
	}
	
    public CookOutput getResult(com.demo.activity.cook.CookInput cookInput) {
    	long startTime = System.nanoTime();
    	List<Order> orders = cookInput.getOrders();
    	for (Order order : orders) {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    	// calculate how long we take here
		long endTime = System.nanoTime();
		int durationSeconds = (int) TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
		System.out.println("Cook(Activity) durationSeconds: " + durationSeconds);
    	return new CookOutput(durationSeconds, orders.size());    	
    }
}
