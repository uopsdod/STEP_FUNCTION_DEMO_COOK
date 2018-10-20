package com.demo;

import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.branch;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.end;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.next;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.parallelState;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.stateMachine;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.taskState;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.builder.StateMachine;
import com.amazonaws.services.stepfunctions.model.CreateStateMachineRequest;
import com.amazonaws.services.stepfunctions.model.CreateStateMachineResult;

/**
 * Hello world!
 *
 */
public class RestaurantStateMachine_01_03_parallel 
{
	static String stateMachineName = "RestaurantStateMachine_parallel";
	
    public static void main( String[] args )
    {
    	/** actually create a state machine **/
        final AWSStepFunctions client = AWSStepFunctionsClientBuilder.standard().withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).build();
     	
    	/** get Lambda and activity workers' arn **/
    	String lambda_prepare_ingredients_agn = "arn:aws:lambda:us-east-1:602307824922:function:PrepareIngredients";
    	String lambda_cook_agn = "arn:aws:lambda:us-east-1:602307824922:function:Cook";
    	String lambda_serve_agn = "arn:aws:lambda:us-east-1:602307824922:function:Serve";
    	String lambda_start_live_show_agn = "arn:aws:lambda:us-east-1:602307824922:function:StartLiveShow";
    	String lambda_end_live_show_agn = "arn:aws:lambda:us-east-1:602307824922:function:EndLiveShow";
    	
    	/** role arn **/
    	String role_agn = "arn:aws:iam::602307824922:role/STEP_FUNCTION_DEMO_COOK";
    	
        /** define state machine **/
        final StateMachine stateMachine = stateMachine()
                .comment("Run a restaurant")
                .startAt("Parallel")
                .state("Parallel", parallelState()
                        .branch(branch()
                        				.startAt("Prepare Ingredients")
		                                .state("Prepare Ingredients", taskState()
		                                        .resource(lambda_prepare_ingredients_agn)
		                                        .transition(next("Cook")))
		                                .state("Cook", taskState()
		                                        .resource(lambda_cook_agn)
		                                        .transition(next("Serve")))
		                                .state("Serve", taskState()
		                                        .resource(lambda_serve_agn)
		                                        .transition(end())))
                        .branch(branch()
                                        .startAt("Start Live Show")
                                        .state("Start Live Show", taskState()
		                                        .resource(lambda_start_live_show_agn)
		                                        .transition(end())))
                        .transition(next("End Live Show"))
                        )
                .state("End Live Show", taskState()
                        .resource(lambda_end_live_show_agn)
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
