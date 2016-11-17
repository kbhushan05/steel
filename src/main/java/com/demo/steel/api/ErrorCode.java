package com.demo.steel.api;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
	
	SUPPLIER_NOT_FOUND("supplier_not_found",HttpStatus.NOT_FOUND),
	INVALID_STATE_FHT("invalid_state_fht", HttpStatus.BAD_REQUEST),
	INVALID_STATE_SAVE("invalid_state_save", HttpStatus.BAD_REQUEST),
	INVALID_STATE_SUBMIT("invalid_state_submit", HttpStatus.BAD_REQUEST),
	INVALID_STATE_UPDATE("invalid_state_update", HttpStatus.BAD_REQUEST),
	INVALID_STATE_APPROVE("invalid_state_approve", HttpStatus.BAD_REQUEST),
	INVALID_STATE_REJECT("invalid_state_reject", HttpStatus.BAD_REQUEST),
	INVALID_ACTION("invalid_action",HttpStatus.BAD_REQUEST), 
	INTERNAL_ERROR("internal_error",HttpStatus.INTERNAL_SERVER_ERROR), 
	STEELMILL_NOT_FOUND("steelmill_not_found",HttpStatus.NOT_FOUND),
	MISSING_REQ_PARAM("missing_req_param",HttpStatus.BAD_REQUEST), 
	INVALID_PAGE("invalid_page",HttpStatus.BAD_REQUEST);
	
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
