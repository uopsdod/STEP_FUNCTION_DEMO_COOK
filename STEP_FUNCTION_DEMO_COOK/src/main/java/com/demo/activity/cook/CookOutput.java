package com.demo.activity.cook;

public class CookOutput {
	int workingSeconds;
	int orderNumber;

	public CookOutput(int workingSeconds, int orderNumber) {
		super();
		this.workingSeconds = workingSeconds;
		this.orderNumber = orderNumber;
	}

	public int getWorkingSeconds() {
		return workingSeconds;
	}

	public void setWorkingSeconds(int workingSeconds) {
		this.workingSeconds = workingSeconds;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int number) {
		this.orderNumber = number;
	}
	
	
	
}
