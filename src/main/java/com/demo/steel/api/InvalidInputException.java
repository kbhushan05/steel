package com.demo.steel.api;

public class InvalidInputException extends ApiException{

	private static final long serialVersionUID = 1L;
	
	public InvalidInputException(ErrorCode errorCode, String message){
		super(errorCode,message);
	}
	public InvalidInputException(ErrorCode errorCode, String message, Throwable t){
		super(errorCode,message, t);
	}
	
}
