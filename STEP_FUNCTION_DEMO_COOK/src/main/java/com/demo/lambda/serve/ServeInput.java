package com.demo.lambda.serve;

public class ServeInput {
	int workingHour;
	int number;
	
	public ServeInput() {}

	public ServeInput(int workingHour, int number) {
		super();
		this.workingHour = workingHour;
		this.number = number;
	}

	public int getWorkingHour() {
		return workingHour;
	}

	public void setWorkingHour(int workingHour) {
		this.workingHour = workingHour;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
