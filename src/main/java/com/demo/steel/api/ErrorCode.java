package com.demo.steel.api;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
	
	SUPPLIER_NOT_FOUND("supplier_not_found",HttpStatus.NOT_FOUND);
	
	private String name;
	private HttpStatus httpStatus;
	
	private ErrorCode(String name, HttpStatus httpStatus){
		this.name = name;
		this.httpStatus = httpStatus;
	}
	public String getName(){
		return name;
	}
	public HttpStatus getHttpStatus(){
		return httpStatus;
	}
}
