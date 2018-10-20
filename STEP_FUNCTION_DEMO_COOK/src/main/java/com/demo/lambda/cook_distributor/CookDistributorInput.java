package com.demo.lambda.cook_distributor;

import java.util.ArrayList;
import java.util.List;

public class CookDistributorInput {
	String ingredients;
	int orderNumber;
	List<Order> orders;
	public CookDistributorInput() {}
	public CookDistributorInput(String ingredients, int orderNumber, Order order) {
		super();
		this.ingredients = ingredients;
		this.orderNumber = orderNumber;
		this.orders = new ArrayList<>();
		this.orders.add(order);
	}
	public CookDistributorInput(String ingredients, int orderNumber, List<Order> orders) {
		super();
		this.ingredients = ingredients;
		this.orderNumber = orderNumber;
		this.orders = orders;
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
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
}
