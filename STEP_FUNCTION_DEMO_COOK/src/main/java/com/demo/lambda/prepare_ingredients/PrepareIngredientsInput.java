package com.demo.lambda.prepare_ingredients;

public class PrepareIngredientsInput {
	int orderNumber;
	
	public PrepareIngredientsInput() {}

	public PrepareIngredientsInput(int orderNumber) {
		super();
		this.orderNumber = orderNumber;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	
}
