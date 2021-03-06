package com.demo.lambda.intern_cook;

import java.util.ArrayList;
import java.util.List;


public class InternCookInput {
	String ingredients;
	int orderNumber;
	List<Order> orders;
	public InternCookInput() {}
	public InternCookInput(String ingredients, int orderNumber, Order order) {
		super();
		this.ingredients = ingredients;
		this.orderNumber = orderNumber;
		this.orders = new ArrayList<>();
		this.orders.add(order);
	}
	public InternCookInput(String ingredients, int orderNumber, List<Order> orders) {
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
