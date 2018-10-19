package com.demo.lambda.serve;

public class ServeOutput {
	int number;
	int workingHour;
	public ServeOutput(int workingHour, int number) {
		super();
		this.workingHour = workingHour;
		this.number = number;
	}
	
	public String getResultMsg() {
		return this.number + " meals are served and it takes " + this.workingHour + " hours";
	}
	
}
