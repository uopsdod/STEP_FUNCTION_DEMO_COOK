package com.demo;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import com.demo.lambda.prepare_ingredients.PrepareIngredients;
/**
 * Unit test for simple App.
 */
public class AppTest 
{
	@Test
	public void testPrepareIngredientResult() {
		PrepareIngredients prepareIngredients = new PrepareIngredients();
		Assert.assertThat(prepareIngredients.getResult().getIngredients(), is("cilantro,lamb,wine,black pepper"));
		Assert.assertThat(prepareIngredients.getResult().getNumber(), is(100));
	}
	
}
