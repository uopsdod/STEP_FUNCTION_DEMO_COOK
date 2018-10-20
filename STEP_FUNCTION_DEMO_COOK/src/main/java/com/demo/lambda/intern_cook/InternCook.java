package com.demo.lambda.intern_cook;

import java.util.Random;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class InternCook implements RequestHandler<InternCookInput, InternCookOutput> {

    public InternCook() {}

    public InternCookOutput getResult(InternCookInput cookInput) {
    	
    	/** inter cook will fail from time to time **/
    	if (!willSucceedThisTime()) {
    		throw new RuntimeException("InternCook screws up this time");
    	}
    	
    	int workingSeconds = cookInput.getOrderNumber() * 10;
    	return new InternCookOutput(workingSeconds, cookInput.getOrderNumber());
    }

	public InternCookOutput handleRequest(InternCookInput event, Context context) {
      context.getLogger().log("Received event: " + event);
      InternCookOutput result = getResult(event);
      return result;
	}
	
	public boolean willSucceedThisTime() {
    	Random randomNum = new Random();
    	int randomSuccess = 1 + randomNum.nextInt(3); // 1 or 2 or 3
    	if (randomSuccess != 1) {
    		return false;
    	}
    	return true;
	}
}
/**
 * test input:
{"ingredients":"cilantro,lamb,wine,black pepper","orderNumber":100}
 * expected output:
{
  "workingSeconds": 1000,
  "orderNumber": 100
}
*/

