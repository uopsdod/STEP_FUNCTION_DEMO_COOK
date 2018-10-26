package com.demo.lambda.serve;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Serve implements RequestHandler<ServeInput, ServeOutput> {

    public Serve() {}

    public ServeOutput getResult(ServeInput event) {
    	return new ServeOutput(event.getWorkingSeconds(), event.getOrderNumber());
    }

	public ServeOutput handleRequest(ServeInput event, Context context) {
      context.getLogger().log("Received event: " + event);
      ServeOutput result = getResult(event);
      return result;
	}
}
