package com.demo.steel.api;

public class ApiError {

	private int httpStatus;
	private String developerMessage;
	private String userMessage;
	private ErrorCode apiErrorCode;
	
	public int getHttpStatus() {
		return httpStatus;
	}
	public ApiError setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
		return this;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}
	public ApiError setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
		return this;
	}
	public String getUserMessage() {
		return userMessage;
	}
	public ApiError setUserMessage(String userMessage) {
		this.userMessage = userMessage;
		return this;
	}
	public ErrorCode getApiErrorCode() {
		return apiErrorCode;
	}
	public ApiError setApiErrorCode(ErrorCode apiErrorCode) {
		this.apiErrorCode = apiErrorCode;
		return this;
	}
	
}
