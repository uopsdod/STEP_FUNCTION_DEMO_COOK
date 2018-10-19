package com.demo;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import static org.hamcrest.CoreMatchers.*;

import com.demo.lambda.cook.Cook;
import com.demo.lambda.cook.CookInput;
import com.demo.lambda.prepare_ingredients.PrepareIngredients;
import com.demo.lambda.prepare_ingredients.PrepareIngredientsInput;
import com.demo.lambda.prepare_ingredients.PrepareIngredientsOutput;
/**
 * Unit test for simple App.
 */
public class AppTest 
{
	int number = 100;
	
	@Test
	public void testPrepareIngredientResult() {
		PrepareIngredients prepareIngredients = new PrepareIngredients();
		PrepareIngredientsInput prepareIngredientsInput = Mockito.mock(PrepareIngredientsInput.class);
		when(prepareIngredientsInput.getNumber()).thenReturn(100);
		
		PrepareIngredientsOutput result = prepareIngredients.getResult(prepareIngredientsInput);
		Assert.assertThat(result.getIngredients(), is("cilantro,lamb,wine,black pepper"));
		Assert.assertThat(result.getNumber(), is(number));
	}
	
	
	@Test
	public void testCookResult() {
		Cook cook = new Cook();
		// mock CookInput result 
		CookInput cookInput = Mockito.mock(CookInput.class);
		when(cookInput.getNumber()).thenReturn(number);

		Assert.assertThat(cook.getResult(cookInput).getWorkingHour(), is(number*10));
	}	
}
