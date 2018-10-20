package com.demo.lambda.intern_cook;

public class InternCookInput {
	String ingredients;
	int orderNumber;
	public InternCookInput() {}
	public InternCookInput(String ingredients, int orderNumber) {
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
