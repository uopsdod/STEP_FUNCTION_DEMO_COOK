package com.demo.lambda.cook_distributor;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClient;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class CookDistributor implements RequestHandler<CookDistributorInput, CookDistributorOutput> {

    public CookDistributor() {}

    public CookDistributorOutput getResult(CookDistributorInput cookInput) {
    	long startTime = System.nanoTime();
    	
    	// invoke lambda asynchronously and gather their result after we invoke them all
    	// advantage: here, we take advantage of the auto-scaling feature of lambda to have a great performance 
    	List<Order> orders = cookInput.getOrders();
		List<Future<InvokeResult>> futures = distirbute(cookInput.getIngredients(), cookInput.getOrderNumber(), orders);
		
		// wait for the lambdas to get back (TODO: it might be good to set some timeout here)
    	waitForProcessing(futures);
		
		// calculate how long we take here
		long endTime = System.nanoTime();
		int durationSeconds = (int) TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
		System.out.println("CookDistributor durationSeconds: " + durationSeconds);
		
    	return new CookDistributorOutput(durationSeconds, orders.size());
    }

    public void waitForProcessing(List<Future<InvokeResult>> futures) {
		for (Future<InvokeResult> future : futures) {
			try {
				InvokeResult invokeResult = future.get();
			    String payload = "";
			    if(invokeResult.getPayload() != null){
			        payload = new String(invokeResult.getPayload().array(), Charset.forName("UTF-8"));
			        System.out.println("CookDistributor payload: " + payload);
			    }else {
			    	System.out.println("CookDistributor no payload");
			    }
			} catch (InterruptedException | ExecutionException e) {
				System.out.println("CookDistributor e: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public List<Future<InvokeResult>> distirbute(String ingredients, int orderNumber, List<Order> orders) {
		Gson gson = new Gson();
		
    	/** actually create a state machine **/
        AWSLambdaAsync lambdaAsyncClient = AWSLambdaAsyncClient.asyncBuilder().withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).build();
		
    	/** get Lambda arn **/
    	String lambda_cook_agn = "arn:aws:lambda:us-east-1:602307824922:function:Cook";
		
        List<Future<InvokeResult>> futures = new ArrayList<>();
    	System.out.println("CookDistributor orders size: " + orders.size());
		for (Order order : orders) {
	        CookDistributorCookOutput cookDistributorCookOutput = new CookDistributorCookOutput(ingredients,orderNumber,order);
	        InvokeRequest invoke = new InvokeRequest();
			invoke.withFunctionName(lambda_cook_agn)
	                .withPayload(gson.toJson(cookDistributorCookOutput)); // TODO - change CookInput (let's do it)
	        Future<InvokeResult> future = lambdaAsyncClient.invokeAsync(invoke);
	        futures.add(future);
		}
		
		return futures;
	}

	public CookDistributorOutput handleRequest(CookDistributorInput event, Context context) {
      context.getLogger().log("Received event: " + event);
      CookDistributorOutput result = getResult(event);
      return result;
	}
}
/**
 * test input:
{"ingredients":"cilantro,lamb,wine,black pepper","orderNumber":100}
{"ingredients":"cilantro,lamb,wine,black pepper","orderNumber":3,"orders":[{"orderId":1},{"orderId":2},{"orderId":3}],"ordersSize":3}
{"ingredients":"cilantro,lamb,wine,black pepper","orderNumber":5,"orders":[{"orderId":101},{"orderId":102},{"orderId":103},{"orderId":104},{"orderId":105}],"ordersSize":5}
 * expected output:
{"workingSeconds":6,"orderNumber":3}
*/
