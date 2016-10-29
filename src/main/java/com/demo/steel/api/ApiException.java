package com.demo.steel.api;

public abstract class ApiException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private ErrorCode errorCode;
	
	protected ApiException(ErrorCode code, String message){
		super(message);
		this.errorCode = code;
	}
	
	protected ApiException(ErrorCode code, String message, Throwable t){
		super(message,t);
		this.errorCode = code;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
