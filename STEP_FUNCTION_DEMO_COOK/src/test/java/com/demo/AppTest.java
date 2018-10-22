package com.demo;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.*;

import com.amazonaws.services.lambda.model.InvokeResult;
import com.demo.lambda.cook.CookOutput;
import com.demo.lambda.cook_distributor.CookDistributor;
import com.demo.lambda.cook_distributor.CookDistributorInput;
import com.demo.lambda.endLiveShow.EndLiveShow;
import com.demo.lambda.endLiveShow.EndLiveShowInput;
import com.demo.lambda.intern_cook.InternCook;
import com.demo.lambda.intern_cook.InternCookInput;
import com.demo.lambda.intern_cook.InternCookOutput;
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
	int number = 1;
	
	@Test
	public void testPrepareIngredientResult() {
		PrepareIngredients prepareIngredients = new PrepareIngredients();
		PrepareIngredientsInput prepareIngredientsInput = Mockito.mock(PrepareIngredientsInput.class);
		when(prepareIngredientsInput.getOrderNumber()).thenReturn(number);
		
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
		
		List<com.demo.lambda.cook.Order> orders = new ArrayList<>();
		/** simulate we got order details for each one **/
		for (int i = 1; i <= number; i++) {
			orders.add(new com.demo.lambda.cook.Order());
		}
		when(cookInput.getOrders()).thenReturn(orders);

		CookOutput result = cook.getResult(cookInput);
		Assert.assertThat(result.getOrderNumber(), is(number));
		Assert.assertTrue(result.getWorkingSeconds() > 0);
	}

	@Test
	public void testActivityCookResult() {
		com.demo.activity.cook.Cook cook = new com.demo.activity.cook.Cook();
		// mock CookInput result 
		com.demo.activity.cook.CookInput cookInput = Mockito.mock(com.demo.activity.cook.CookInput.class);
		when(cookInput.getOrderNumber()).thenReturn(number);
		
		List<com.demo.activity.cook.Order> orders = new ArrayList<>();
		/** simulate we got order details for each one **/
		for (int i = 1; i <= number; i++) {
			orders.add(new com.demo.activity.cook.Order());
		}
		when(cookInput.getOrders()).thenReturn(orders);

		com.demo.activity.cook.CookOutput result = cook.getResult(cookInput);
		Assert.assertThat(result.getOrderNumber(), is(number));
		Assert.assertTrue(result.getWorkingSeconds() > 0);
	}
	
	@Test
	public void testServeResult() {
		Serve serve = new Serve();
		ServeInput serveInput = Mockito.mock(ServeInput.class);
		when(serveInput.getWorkingSeconds()).thenReturn(number*10);
		when(serveInput.getOrderNumber()).thenReturn(number);

		Assert.assertThat(serve.getResult(serveInput).getResultMsg(), is(number + " meals are served and it takes " + number*10 + " seconds"));
	}		
	
	@Test
	public void testInternCookResultSuccess() {
		/** partial mock **/
		InternCook internCook = spy(new InternCook());
		when(internCook.willSucceedThisTime()).thenReturn(true);
		
		InternCookInput internCookInput = Mockito.mock(InternCookInput.class);
		when(internCookInput.getOrderNumber()).thenReturn(number);
		
		List<com.demo.lambda.intern_cook.Order> orders = new ArrayList<>();
		/** simulate we got order details for each one **/
		for (int i = 1; i <= number; i++) {
			orders.add(new com.demo.lambda.intern_cook.Order());
		}
		when(internCookInput.getOrders()).thenReturn(orders);		

		InternCookOutput result = internCook.getResult(internCookInput);
		Assert.assertThat(result.getOrderNumber(), is(number));
		Assert.assertTrue(result.getWorkingSeconds() > 0);
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
	
	@Test
	public void testCookDistributorResult() {
//		CookDistributorInput cookDistributorInput = Mockito.mock(CookDistributorInput.class);
//		List<com.demo.lambda.cook_distributor.Order> orders = new ArrayList<>();
//		/** simulate we got order details for each one **/
//		for (int i = 1; i <= number; i++) {
//			orders.add(new com.demo.lambda.cook_distributor.Order());
//		}
//		when(cookDistributorInput.getOrders()).thenReturn(orders);
		
		// it doesn't test anything now 
		/** partial mock **/ 
//		CookDistributor cookDistributor = spy(new CookDistributor());
//		when(cookDistributor.getResult(cookDistributorInput)).thenReturn(new ArrayList<Future<InvokeResult>>());
////		Mockito.doNothing().when(cookDistributor).waitForProcessing(new ArrayList<Future<InvokeResult>>());
//		
//		Assert.assertThat(cookDistributor.getResult(cookDistributorInput).getOrderNumber(), is(number));
		// TODO: add second check (howerver, it's zero second now)
	}
}
