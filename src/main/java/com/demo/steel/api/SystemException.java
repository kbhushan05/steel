package com.demo.steel.api;

public class SystemException extends ApiException{

	private static final long serialVersionUID = 1L;

	public SystemException(ErrorCode code, String message) {
		super(code, message);
	}
	
	public SystemException(ErrorCode code, String message, Throwable t) {
		super(code, message, t);
	}

}
