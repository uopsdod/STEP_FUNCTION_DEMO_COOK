package com.demo.lambda.cook;

public class CookInput {
	String ingredients;
	int orderNumber;
	public CookInput() {}
	public CookInput(String ingredients, int orderNumber) {
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
