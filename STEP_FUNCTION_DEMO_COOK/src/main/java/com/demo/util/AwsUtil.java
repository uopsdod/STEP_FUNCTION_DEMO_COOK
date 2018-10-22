package com.demo.util;

import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.builder.StateMachine;
import com.amazonaws.services.stepfunctions.model.CreateStateMachineRequest;
import com.amazonaws.services.stepfunctions.model.CreateStateMachineResult;
import com.amazonaws.services.stepfunctions.model.StateMachineAlreadyExistsException;
import com.amazonaws.services.stepfunctions.model.UpdateStateMachineRequest;
import com.amazonaws.services.stepfunctions.model.UpdateStateMachineResult;

public class AwsUtil {
	public static void checkEnvVariables() {
		assertEvnVariableExist("aws.region");
	}
	
	private static void assertEvnVariableExist(String str) {
		if (!isStrExist(System.getProperty(str))) {
			throw new RuntimeException("environment variable " + str + " not found. Please provide.");
		}		
	}
	
	public static boolean isStrExist(String str) {
		if (null != str && !str.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public static void printEnvProperties() {
		Properties props = System.getProperties();
		Set<String> keys = props.stringPropertyNames();
	    for (String key : keys) {
	      System.out.println(key + " : " + props.getProperty(key));
	    }
	}
	
	public static String getStateMachineArnByStateMachineAlreadyExistsException(StateMachineAlreadyExistsException e) {
		String result = "";
    	Pattern pattern = Pattern.compile("State Machine Already Exists: '(.*?)'");
		Matcher matcher = pattern.matcher(e.getErrorMessage());

		if (matcher.find()) {
			result = matcher.group(1);
		}		
		
		return result;
	}
	
	public static void createOrUpdateStateMachine(AWSStepFunctions client, StateMachine stateMachine, String role_agn,
			String stateMachineName) {
        try {
        	CreateStateMachineResult createStateMachine = client.createStateMachine(new CreateStateMachineRequest()
        			.withName(stateMachineName)
        			.withRoleArn(role_agn)
        			.withDefinition(stateMachine));
        	System.out.println(createStateMachine.getStateMachineArn());
        }catch(StateMachineAlreadyExistsException e) {
        	System.out.println(e.getErrorMessage());
        	String stateMachineArn = AwsUtil.getStateMachineArnByStateMachineAlreadyExistsException(e);
			if (AwsUtil.isStrExist(stateMachineArn)) {
				UpdateStateMachineResult updateStateMachine = client.updateStateMachine(new UpdateStateMachineRequest().withStateMachineArn(stateMachineArn).withDefinition(stateMachine.toJson()));
				System.out.println(stateMachineArn + " is updated at " + updateStateMachine.getUpdateDate());
			}else {
				e.printStackTrace();
			}
        }
		
	}	
	
}
