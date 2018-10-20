package com.demo.lambda.endLiveShow;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class EndLiveShow implements RequestHandler<EndLiveShowInput, EndLiveShowOutput> {

    public EndLiveShow() {}

    public EndLiveShowOutput getResult(EndLiveShowInput cookInput) {
    	return new EndLiveShowOutput("Ended TV Show");
    }

	public EndLiveShowOutput handleRequest(EndLiveShowInput event, Context context) {
      context.getLogger().log("Received event: " + event);
      EndLiveShowOutput result = getResult(event);
      return result;
	}
}
/**
 * test input:
 * expected output:
*/

