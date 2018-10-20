package com.demo.lambda.cook_distributor;

import java.util.List;

public class CookDistributorInput {
	List<Order> orders;
	public CookDistributorInput() {}
	public CookDistributorInput(List<Order> orders) {
		super();
		this.orders = orders;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
}
