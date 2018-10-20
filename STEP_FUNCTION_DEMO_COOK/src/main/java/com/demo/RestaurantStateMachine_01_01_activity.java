package com.demo;

import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.end;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.next;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.stateMachine;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.taskState;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.builder.StateMachine;
import com.amazonaws.services.stepfunctions.model.CreateActivityRequest;
import com.amazonaws.services.stepfunctions.model.CreateActivityResult;
import com.amazonaws.services.stepfunctions.model.CreateStateMachineRequest;
import com.amazonaws.services.stepfunctions.model.CreateStateMachineResult;

/**
 * Hello world!
 *
 */
public class RestaurantStateMachine_01_01_activity 
{
	static String stateMachineName = "RestaurantStateMachine_activity";
	
    public static void main( String[] args )
    {
    	/** get an AWS Setp function client **/
    	final AWSStepFunctions client = AWSStepFunctionsClientBuilder.standard().withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).build();
    	
    	/** get Lambda and activity workers' arn **/
    	String lambda_prepare_ingredients_agn = "arn:aws:lambda:us-east-1:602307824922:function:PrepareIngredients";
    	String lambda_serve_agn = "arn:aws:lambda:us-east-1:602307824922:function:Serve";
    	
    	/** get activity agn **/
    	CreateActivityResult createActivity = client.createActivity(new CreateActivityRequest().withName("Cook"));
    	String activity_cook_agn = createActivity.getActivityArn();
    	
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
                        .resource(activity_cook_agn) // the only difference. We change lambda to activity here.
                        .transition(next("Serve")))
                .state("Serve", taskState()
                        .resource(lambda_serve_agn)
                        .transition(end()))
                .build();
        System.out.println(stateMachine.toPrettyJson());
        
        /** actually create a state machine **/
        CreateStateMachineResult createStateMachine = client.createStateMachine(new CreateStateMachineRequest()
                                                  .withName(stateMachineName)
                                                  .withRoleArn(role_agn)
                                                  .withDefinition(stateMachine));
        System.out.println(createStateMachine.getStateMachineArn());
    }
}
