package com.demo.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.demo.entity.CookInput;
import com.demo.entity.CookOutput;

public class Cook implements RequestHandler<CookInput, CookOutput> {

    public Cook() {}

    private CookOutput getResult(CookInput cookInput) {
    	int workingHourInSec = cookInput.getNumber() * 10;
    	return new CookOutput(workingHourInSec);
    }

	public CookOutput handleRequest(CookInput event, Context context) {
      context.getLogger().log("Received event: " + event);
      CookOutput result = getResult(event);
      return result;
	}
}
/**
 * test data: 
 * {"ingredients":"cilantro,lamb,wine,black pepper","number":100}
 */
