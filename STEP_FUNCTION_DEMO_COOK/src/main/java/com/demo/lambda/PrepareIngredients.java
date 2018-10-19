package com.demo.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class PrepareIngredients implements RequestHandler<Object, String> {

    public PrepareIngredients() {}

    private String getResult() {
    	return "cilantro,lamb,wine,black pepper";
    }

	public String handleRequest(Object event, Context context) {
      context.getLogger().log("Received event: " + event);
      String result = getResult();
      return result;
	}
    
}