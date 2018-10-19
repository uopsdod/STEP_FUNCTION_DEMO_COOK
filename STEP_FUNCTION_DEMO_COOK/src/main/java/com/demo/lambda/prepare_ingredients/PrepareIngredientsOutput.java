package com.demo.lambda.prepare_ingredients;

public class PrepareIngredientsOutput {
	String ingredients;
	int number;
	public PrepareIngredientsOutput(String ingredients, int number) {
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
