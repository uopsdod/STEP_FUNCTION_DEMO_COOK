package com.demo;

import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.end;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.next;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.retrier;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.stateMachine;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.taskState;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.builder.StateMachine;
import com.amazonaws.services.stepfunctions.builder.states.Catcher;
import com.amazonaws.services.stepfunctions.builder.states.PassState;
import com.amazonaws.services.stepfunctions.model.CreateStateMachineRequest;
import com.amazonaws.services.stepfunctions.model.CreateStateMachineResult;
import com.amazonaws.services.stepfunctions.model.StateMachineAlreadyExistsException;
import com.amazonaws.services.stepfunctions.model.UpdateStateMachineRequest;
import com.amazonaws.services.stepfunctions.model.UpdateStateMachineResult;
import com.demo.util.AwsUtil;

/**
 * Hello world!
 *
 */
public class RestaurantStateMachine_01_02_01_retry_catch
{
	static String stateMachineName = "RestaurantStateMachine_retry_catch";
	
    public static void main( String[] args )
    {
    	/** check parameter **/
    	AwsUtil.checkEnvVariables();
    	
    	/** actually create a state machine **/
        final AWSStepFunctions client = AWSStepFunctionsClientBuilder.standard().withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).build();
     	
    	/** get Lambda and activity workers' arn **/
    	String lambda_prepare_ingredients_agn = "arn:aws:lambda:us-east-1:602307824922:function:PrepareIngredients";
    	String lambda_intern_cook_agn = "arn:aws:lambda:us-east-1:602307824922:function:InternCook";
    	String lambda_serve_agn = "arn:aws:lambda:us-east-1:602307824922:function:Serve";
    	
    	/** role arn **/
    	String role_agn = "arn:aws:iam::602307824922:role/STEP_FUNCTION_DEMO_COOK";
    	
        Catcher build = Catcher.builder()
		        .catchAll()
		        .transition(next("Catch All Fallback")).build();
		/** define state machine **/
        final StateMachine stateMachine = stateMachine()
                .comment("Run a restaurant")
                .startAt("Prepare Ingredients")
                .state("Prepare Ingredients", taskState()
                        .resource(lambda_prepare_ingredients_agn)
                        .transition(next("InternCook")))
                .state("InternCook", taskState() // inter cook will fail from time to time 
                        .resource(lambda_intern_cook_agn)
                        .retrier(retrier()
                                .retryOnAllErrors()
                                .intervalSeconds(3)
                                .maxAttempts(5)
                                .backoffRate(2.0))
                        .transition(next("Serve"))
                        .catcher(Catcher.builder()
                		        .catchAll()
                		        .transition(next("Notify Chef"))))
                .state("Serve", taskState()
                        .resource(lambda_serve_agn)
                        .transition(end()))
                .state("Notify Chef", PassState.builder()
                		.result("{\"resukt\": \"Hey Chef, I wasted all the ingredients.\"}")
                		.transition(end())) 
                               
                .build();
        System.out.println(stateMachine.toPrettyJson());
        
        /** actually create a state machine **/
        AwsUtil.createOrUpdateStateMachine(client, stateMachine, role_agn, stateMachineName);
    }


}
