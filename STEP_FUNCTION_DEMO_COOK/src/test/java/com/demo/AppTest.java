package com.demo;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import static org.hamcrest.CoreMatchers.*;

import com.demo.lambda.cook.Cook;
import com.demo.lambda.cook.CookInput;
import com.demo.lambda.prepare_ingredients.PrepareIngredients;
/**
 * Unit test for simple App.
 */
public class AppTest 
{
	int number = 100;
	
	@Test
	public void testPrepareIngredientResult() {
		PrepareIngredients prepareIngredients = new PrepareIngredients();
		Assert.assertThat(prepareIngredients.getResult().getIngredients(), is("cilantro,lamb,wine,black pepper"));
		Assert.assertThat(prepareIngredients.getResult().getNumber(), is(number));
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
