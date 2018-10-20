package com.demo.lambda.prepare_ingredients;

public class Order {
	private static int staticCount = 0;
	private int orderId;
	public Order() {
		this.orderId = ++Order.staticCount;
	}
	public int getOrderId() {
		return orderId;
	}
	
}
