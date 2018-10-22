package com.demo.lambda.prepare_ingredients;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class PrepareIngredients implements RequestHandler<PrepareIngredientsInput, PrepareIngredientsOutput> {

    public PrepareIngredients() {}

    public PrepareIngredientsOutput getResult(PrepareIngredientsInput event) {
    	return new PrepareIngredientsOutput("cilantro,lamb,wine,black pepper", event.getOrderNumber());
    }

	public PrepareIngredientsOutput handleRequest(PrepareIngredientsInput event, Context context) {
      context.getLogger().log("Received event: " + event);
      PrepareIngredientsOutput result = getResult(event);
      return result;
	}
    
}
