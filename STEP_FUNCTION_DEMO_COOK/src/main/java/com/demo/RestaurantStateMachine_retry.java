package com.demo;

import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.*;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.builder.ErrorCodes;
import com.amazonaws.services.stepfunctions.builder.StateMachine;
import com.amazonaws.services.stepfunctions.model.CreateStateMachineRequest;
import com.amazonaws.services.stepfunctions.model.CreateStateMachineResult;
import com.amazonaws.services.stepfunctions.model.ListStateMachinesRequest;

/**
 * Hello world!
 *
 */
public class RestaurantStateMachine_retry 
{
	static String stateMachineName = "RestaurantStateMachine_retry";
	
    public static void main( String[] args )
    {
    	/** get aws credential profile **/
    	String credential_profile = "stsai";
 
    	/** actually create a state machine **/
        final AWSStepFunctions client = AWSStepFunctionsClientBuilder.standard().withCredentials(new ProfileCredentialsProvider(credential_profile)).build();
     	
    	/** get Lambda and activity workers' arn **/
    	String lambda_prepare_ingredients_agn = "arn:aws:lambda:us-east-1:602307824922:function:PrepareIngredients";
    	String lambda_intern_cook_agn = "arn:aws:lambda:us-east-1:602307824922:function:InternCook";
    	String lambda_serve_agn = "arn:aws:lambda:us-east-1:602307824922:function:Serve";
    	
    	/** role arn **/
    	String role_agn = "arn:aws:iam::602307824922:role/STEP_FUNCTION_DEMO_COOK";
    	
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
                                .intervalSeconds(5)
                                .maxAttempts(6)
                                .backoffRate(2.0))
                        .transition(next("Serve")))
                .state("Serve", taskState()
                        .resource(lambda_serve_agn)
                        .transition(end()))
                .build();
        System.out.println(stateMachine.toPrettyJson());
        
        CreateStateMachineResult createStateMachine = client.createStateMachine(new CreateStateMachineRequest()
                                                  .withName(stateMachineName)
                                                  .withRoleArn(role_agn)
                                                  .withDefinition(stateMachine));
        System.out.println(createStateMachine.getStateMachineArn());
    }
}