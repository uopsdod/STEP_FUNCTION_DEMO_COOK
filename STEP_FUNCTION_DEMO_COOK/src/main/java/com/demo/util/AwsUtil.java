package com.demo.lambda.util;

import java.util.Properties;
import java.util.Set;

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
}
