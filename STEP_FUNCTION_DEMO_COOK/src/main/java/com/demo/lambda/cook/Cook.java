package com.demo.lambda.cook;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Cook implements RequestHandler<CookInput, CookOutput> {

    public Cook() {}

    public CookOutput getResult(CookInput cookInput) {
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
		System.out.println("Cook durationSeconds: " + durationSeconds);
    	return new CookOutput(durationSeconds, orders.size());
    }

	public CookOutput handleRequest(CookInput event, Context context) {
      context.getLogger().log("Received event: " + event);
      CookOutput result = getResult(event);
      return result;
	}
}
