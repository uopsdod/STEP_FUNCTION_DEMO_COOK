package com.demo.lambda.serve;

public class ServeOutput {
	int orderNumber;
	int workingHour;
	public ServeOutput(int workingHour, int orderNumber) {
		super();
		this.workingHour = workingHour;
		this.orderNumber = orderNumber;
	}
	
	public String getResultMsg() {
		return this.orderNumber + " meals are served and it takes " + this.workingHour + " hours";
	}
	
}
