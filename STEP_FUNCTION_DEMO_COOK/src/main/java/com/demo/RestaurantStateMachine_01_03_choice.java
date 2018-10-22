package com.demo;

import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.*;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.builder.StateMachine;
import com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder;
import com.amazonaws.services.stepfunctions.builder.states.PassState;
import com.demo.util.AwsUtil;

/**
 * Hello world!
 *
 */
public class RestaurantStateMachine_01_03_choice 
{
	static String stateMachineName = "RestaurantStateMachine_choice";
	
    public static void main( String[] args )
    {
    	/** check parameter **/
    	AwsUtil.checkEnvVariables();    	
    	
    	/** actually create a state machine **/
        final AWSStepFunctions client = AWSStepFunctionsClientBuilder.standard().withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).build();
     	
    	/** get Lambda and activity workers' arn **/
    	String lambda_prepare_ingredients_agn = "arn:aws:lambda:us-east-1:602307824922:function:PrepareIngredients";
    	String lambda_cook_agn = "arn:aws:lambda:us-east-1:602307824922:function:Cook";
    	String lambda_serve_agn = "arn:aws:lambda:us-east-1:602307824922:function:Serve";
    	
    	/** role arn **/
    	String role_agn = "arn:aws:iam::602307824922:role/STEP_FUNCTION_DEMO_COOK";
    	
        /** define state machine **/
        final StateMachine stateMachine = stateMachine()
                .comment("Run a restaurant")
                .startAt("Is Holiday?")
                .state("Is Holiday?", choiceState()
                        .choice(choice()
                                        .transition(next("Vacation"))
                                        .condition(eq("$.isHoliday", true)))
                        .defaultStateName("Prepare Ingredients"))                
                
                .state("Prepare Ingredients", taskState()
                        .resource(lambda_prepare_ingredients_agn)
                        .transition(next("Cook")))
                .state("Cook", taskState()
                        .resource(lambda_cook_agn)
                        .transition(next("Serve")))
                .state("Serve", taskState()
                        .resource(lambda_serve_agn)
                        .transition(end()))
                .state("Vacation", PassState.builder()
                		.result("{\"result\": \"Take a day off.\"}")
                		.transition(end()))                 
                .build();
        System.out.println(stateMachine.toPrettyJson());

        /** actually create a state machine **/
        AwsUtil.createOrUpdateStateMachine(client, stateMachine, role_agn, stateMachineName);
    }
}
