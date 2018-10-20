package com.demo.lambda.intern_cook;

public class InternCookOutput {
	int workingHour;
	int orderNumber;

	public InternCookOutput(int workingHour, int orderNumber) {
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
