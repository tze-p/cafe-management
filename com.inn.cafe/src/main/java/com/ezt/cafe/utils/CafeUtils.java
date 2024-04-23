package com.ezt.cafe.utils;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtils {

	private CafeUtils() {
	}
	
	public static ResponseEntity<String> getResponseEntity(String message, HttpStatus status) {
		return new ResponseEntity<String>("{\"message\":\"" + message + "\" }", status);
	}

	public static void log(String message) {
		System.out.println(">> " + message);
	}
	
	public static void log(String message, Map<String, String> requestMap) { 		
		String tmpString = "";		
		for (Map.Entry<String,String> entry : requestMap.entrySet()) { 
			tmpString = tmpString.concat(entry.getKey() + ":" + entry.getValue());
			tmpString = tmpString.concat("; ");
		}		
		System.out.println(">> " + message + ":- " + tmpString);
	}
	
}
