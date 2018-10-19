package com.demo.lambda.cook;

public class CookInput {
	String ingredients;
	int number;
	public CookInput() {}
	public CookInput(String ingredients, int number) {
		super();
		this.ingredients = ingredients;
		this.number = number;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
}
