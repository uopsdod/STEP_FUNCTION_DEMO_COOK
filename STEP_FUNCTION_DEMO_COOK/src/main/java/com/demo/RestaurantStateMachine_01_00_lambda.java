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
public class RestaurantStateMachine_01_00_lambda 
{
	static String stateMachineName = "RestaurantStateMachine_lambda";
	
    public static void main( String[] args )
    {
    	/** get aws credential profile **/
    	String credential_profile = "stsai";
 
    	/** actually create a state machine **/
        final AWSStepFunctions client = AWSStepFunctionsClientBuilder.standard().withCredentials(new ProfileCredentialsProvider(credential_profile)).build();
     	
    	/** get Lambda and activity workers' arn **/
    	String lambda_prepare_ingredients_agn = "arn:aws:lambda:us-east-1:602307824922:function:PrepareIngredients";
    	String lambda_cook_agn = "arn:aws:lambda:us-east-1:602307824922:function:Cook";
    	String lambda_serve_agn = "arn:aws:lambda:us-east-1:602307824922:function:Serve";
    	
    	/** role arn **/
    	String role_agn = "arn:aws:iam::602307824922:role/STEP_FUNCTION_DEMO_COOK";
    	
        /** define state machine **/
        final StateMachine stateMachine = stateMachine()
                .comment("Run a restaurant")
                .startAt("Prepare Ingredients")
                .state("Prepare Ingredients", taskState()
                        .resource(lambda_prepare_ingredients_agn)
                        .transition(next("Cook")))
                .state("Cook", taskState()
                        .resource(lambda_cook_agn)
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
