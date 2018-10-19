package com.demo;

import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.*;

import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.builder.ErrorCodes;
import com.amazonaws.services.stepfunctions.builder.StateMachine;
import com.amazonaws.services.stepfunctions.model.CreateStateMachineRequest;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	/** get Lambda and activity workers' arn **/
    	String lambda_prepare_ingredients_agn = "";
    	String lambda_cook_agn = "";
    	String lambda_serve_agn = "";
    	
    	/** role arn **/
    	String role_agn = "arn:aws:iam::602307824922:role/STEP_FUNCTION_DEMO_COOK";
    	
        /** define state machine **/
        final StateMachine stateMachine = stateMachine()
                .comment("A Hello World example of the Amazon States Language using an AWS Lambda Function")
                .startAt("Prepare Ingredients")
                .state("Prepare Ingredients", taskState()
                        .resource(lambda_prepare_ingredients_agn)
                        .transition(end()))
                .build();
        System.out.println(stateMachine.toPrettyJson());    	
        
        /** actually create a state machine **/
        final AWSStepFunctions client = AWSStepFunctionsClientBuilder.defaultClient();
        client.createStateMachine(new CreateStateMachineRequest()
                                                  .withName("Hello World State Machine")
                                                  .withRoleArn(role_agn)
                                                  .withDefinition(stateMachine));
        
    }
}
