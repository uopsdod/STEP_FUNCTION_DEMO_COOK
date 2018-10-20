package com.demo.lambda.prepare_ingredients;

import java.util.ArrayList;
import java.util.List;

public class PrepareIngredientsOutput {
	String ingredients;
	int orderNumber;
	List<Order> orders;
	public PrepareIngredientsOutput(String ingredients, int orderNumber) {
		super();
		this.ingredients = ingredients;
		this.orderNumber = orderNumber;
		this.orders = new ArrayList<>();
		/** simulate we got order details for each one **/
		for (int i = 1; i <= orderNumber; i++) {
			this.orders.add(new Order());
		}
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
	public int getOrdersSize() {
		return this.orders.size();
	}
	public List<Order> getOrders() {
		return orders;
	}
	
}
