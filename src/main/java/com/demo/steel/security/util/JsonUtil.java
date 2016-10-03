package com.demo.steel.security.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	public static String stringify(Object o) throws JsonProcessingException{
		
		ObjectMapper mapper = new ObjectMapper();
		String s = mapper.writeValueAsString(o);
		return s;
	}
	
	public static <T> T getObject(String s, Class<T> clazz) throws JsonProcessingException,IOException{
		ObjectMapper mapper = new ObjectMapper();
		T t = mapper.readValue(s, clazz);
		return t;
	}
}
