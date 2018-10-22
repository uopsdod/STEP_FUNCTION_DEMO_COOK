package com.demo.lambda.intern_cook;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class InternCook implements RequestHandler<InternCookInput, InternCookOutput> {

    public InternCook() {}

    public InternCookOutput getResult(InternCookInput cookInput) {
    	
    	/** inter cook will fail from time to time **/
    	if (!willSucceedThisTime()) {
    		throw new RuntimeException("InternCook screws up this time");
    	}
    	
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
		System.out.println("InternCook durationSeconds: " + durationSeconds);
    	return new InternCookOutput(durationSeconds, orders.size());
    }

	public InternCookOutput handleRequest(InternCookInput event, Context context) {
      context.getLogger().log("Received event: " + event);
      InternCookOutput result = getResult(event);
      return result;
	}
	
	public boolean willSucceedThisTime() {
    	Random randomNum = new Random();
    	int randomSuccess = 1 + randomNum.nextInt(3); // 1 or 2 or 3
    	if (randomSuccess == 1) {
    		return false;
    	}
    	return true;
	}
}
/**
 * test input:
{"ingredients":"cilantro,lamb,wine,black pepper","orderNumber":1,"orders":[{"orderId":101}],"ordersSize":1}
 * expected output:
{
  "workingSeconds": 1000,
  "orderNumber": 100
}
*/

