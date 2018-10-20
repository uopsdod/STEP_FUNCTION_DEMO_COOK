package com.demo.lambda.cook;

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
