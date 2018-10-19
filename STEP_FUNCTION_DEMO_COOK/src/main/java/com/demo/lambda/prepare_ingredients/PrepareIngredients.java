package com.demo.lambda.prepare_ingredients;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class PrepareIngredients implements RequestHandler<PrepareIngredientsInput, PrepareIngredientsOutput> {

    public PrepareIngredients() {}

    private PrepareIngredientsOutput getResult() {
    	return new PrepareIngredientsOutput("cilantro,lamb,wine,black pepper", 100);
    }

	public PrepareIngredientsOutput handleRequest(PrepareIngredientsInput event, Context context) {
      context.getLogger().log("Received event: " + event);
      PrepareIngredientsOutput result = getResult();
      return result;
	}
    
}