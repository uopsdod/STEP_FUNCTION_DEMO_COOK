package com.demo;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.spy;

import static org.hamcrest.CoreMatchers.*;

import com.demo.lambda.endLiveShow.EndLiveShow;
import com.demo.lambda.endLiveShow.EndLiveShowInput;
import com.demo.lambda.intern_cook.InternCook;
import com.demo.lambda.intern_cook.InternCookInput;
import com.demo.lambda.prepare_ingredients.PrepareIngredients;
import com.demo.lambda.prepare_ingredients.PrepareIngredientsInput;
import com.demo.lambda.prepare_ingredients.PrepareIngredientsOutput;
import com.demo.lambda.serve.Serve;
import com.demo.lambda.serve.ServeInput;
import com.demo.lambda.startLiveShow.StartLiveShow;
import com.demo.lambda.startLiveShow.StartLiveShowInput;
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
		when(prepareIngredientsInput.getOrderNumber()).thenReturn(100);
		
		PrepareIngredientsOutput result = prepareIngredients.getResult(prepareIngredientsInput);
		Assert.assertThat(result.getIngredients(), is("cilantro,lamb,wine,black pepper"));
		Assert.assertThat(result.getOrderNumber(), is(number));
		Assert.assertThat(result.getOrdersSize(), is(number));
	}
	
	
	@Test
	public void testCookResult() {
		com.demo.lambda.cook.Cook cook = new com.demo.lambda.cook.Cook();
		// mock CookInput result 
		com.demo.lambda.cook.CookInput cookInput = Mockito.mock(com.demo.lambda.cook.CookInput.class);
		when(cookInput.getOrderNumber()).thenReturn(number);

		Assert.assertThat(cook.getResult(cookInput).getOrderNumber(), is(number));
		Assert.assertThat(cook.getResult(cookInput).getWorkingHour(), is(number*10));
	}

	@Test
	public void testActivityCookResult() {
		com.demo.activity.Cook cook = new com.demo.activity.Cook();
		// mock CookInput result 
		com.demo.activity.CookInput cookInput = Mockito.mock(com.demo.activity.CookInput.class);
		when(cookInput.getOrderNumber()).thenReturn(number);

		Assert.assertThat(cook.getResult(cookInput).getOrderNumber(), is(number));
		Assert.assertThat(cook.getResult(cookInput).getWorkingHour(), is(number*10));
	}
	
	@Test
	public void testServeResult() {
		Serve serve = new Serve();
		ServeInput serveInput = Mockito.mock(ServeInput.class);
		when(serveInput.getWorkingHour()).thenReturn(number*10);
		when(serveInput.getOrderNumber()).thenReturn(number);

		Assert.assertThat(serve.getResult(serveInput).getResultMsg(), is(number + " meals are served and it takes " + number*10 + " hours"));
	}		
	
	@Test
	public void testInternCookResultSuccess() {
		/** partial mock **/
		InternCook internCook = spy(new InternCook());
		when(internCook.willSucceedThisTime()).thenReturn(true);
		
		InternCookInput internCookInput = Mockito.mock(InternCookInput.class);
		when(internCookInput.getOrderNumber()).thenReturn(number);

		Assert.assertThat(internCook.getResult(internCookInput).getOrderNumber(), is(number));
		Assert.assertThat(internCook.getResult(internCookInput).getWorkingHour(), is(number*10));
	}	
	
	@Test(expected = RuntimeException.class)
	public void testInternCookResultFailure() {
		/** partial mock **/
		InternCook internCook = spy(new InternCook());
		when(internCook.willSucceedThisTime()).thenReturn(false);
		
		InternCookInput internCookInput = Mockito.mock(InternCookInput.class);

		internCook.getResult(internCookInput); // expect RuntimeException here 
	}	
	
	@Test
	public void testStartLiveShowResult() {
		StartLiveShow startLiveShow = new StartLiveShow();
		StartLiveShowInput startLiveShowInput = Mockito.mock(StartLiveShowInput.class);
		Assert.assertThat(startLiveShow.getResult(startLiveShowInput).getResult(), is("Started TV Show"));
	}	

	@Test
	public void testEndLiveShowResult() {
		EndLiveShow endLiveShow = new EndLiveShow();
		EndLiveShowInput endLiveShowInput = Mockito.mock(EndLiveShowInput.class);
		Assert.assertThat(endLiveShow.getResult(endLiveShowInput).getResult(), is("Ended TV Show"));
	}	
	
}
