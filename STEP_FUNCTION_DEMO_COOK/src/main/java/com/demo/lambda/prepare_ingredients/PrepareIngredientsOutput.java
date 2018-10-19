package com.demo.lambda.prepare_ingredients;

public class PrepareIngredientsOutput {
	String ingredients;
	int orderNumber;
	public PrepareIngredientsOutput(String ingredients, int orderNumber) {
		super();
		this.ingredients = ingredients;
		this.orderNumber = orderNumber;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	
}
