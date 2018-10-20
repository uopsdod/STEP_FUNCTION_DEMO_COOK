package com.demo.lambda.prepare_ingredients;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
/**
 * test input:
 {"orderNumber": 1}
 {"orderNumber": 3} 
 {"orderNumber": 5}
 {"orderNumber": 100}
 * expected output:
 * {
 *   "ingredients": "cilantro,lamb,wine,black pepper",
 *   "orderNumber": 100
 * }
 */
