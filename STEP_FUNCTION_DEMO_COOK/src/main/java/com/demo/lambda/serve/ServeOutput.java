package com.demo.lambda.serve;

public class ServeOutput {
	int orderNumber;
	int workingSeconds;
	public ServeOutput(int workingSeconds, int orderNumber) {
		super();
		this.workingSeconds = workingSeconds;
		this.orderNumber = orderNumber;
	}
	
	public String getResultMsg() {
		return this.orderNumber + " meals are served and it takes " + this.workingSeconds + " seconds";
	}
	
}
