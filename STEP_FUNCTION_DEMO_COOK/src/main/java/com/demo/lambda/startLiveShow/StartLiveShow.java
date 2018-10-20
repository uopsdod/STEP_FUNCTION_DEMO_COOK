package com.demo.lambda.startLiveShow;

import java.util.Random;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class StartLiveShow implements RequestHandler<StartLiveShowInput, StartLiveShowOutput> {

    public StartLiveShow() {}

    public StartLiveShowOutput getResult(StartLiveShowInput cookInput) {
    	return new StartLiveShowOutput("Started TV Show");
    }

	public StartLiveShowOutput handleRequest(StartLiveShowInput event, Context context) {
      context.getLogger().log("Received event: " + event);
      StartLiveShowOutput result = getResult(event);
      return result;
	}
}
/**
 * test input:
 * expected output:
*/

