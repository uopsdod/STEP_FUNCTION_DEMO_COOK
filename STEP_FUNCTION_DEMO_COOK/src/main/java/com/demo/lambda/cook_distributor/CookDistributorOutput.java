package com.demo.lambda.cook_distributor;

public class CookDistributorOutput {
	int workingHour;
	int orderNumber;

	public CookDistributorOutput(int workingHour, int orderNumber) {
		super();
		this.workingHour = workingHour;
		this.orderNumber = orderNumber;
	}

	public int getWorkingHour() {
		return workingHour;
	}

	public void setWorkingHour(int workingHour) {
		this.workingHour = workingHour;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int number) {
		this.orderNumber = number;
	}
}