package com.demo.lambda.cook;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Cook implements RequestHandler<CookInput, CookOutput> {

    public Cook() {}

    public CookOutput getResult(CookInput cookInput) {
    	int workingHourInSec = cookInput.getOrderNumber() * 10;
    	return new CookOutput(workingHourInSec, cookInput.getOrderNumber());
    }

	public CookOutput handleRequest(CookInput event, Context context) {
      context.getLogger().log("Received event: " + event);
      CookOutput result = getResult(event);
      return result;
	}
}
/**
 * test data: 
 * {"ingredients":"cilantro,lamb,wine,black pepper","orderNumber":100}
 */
