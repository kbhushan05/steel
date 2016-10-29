package com.demo.steel.api;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ApiError> handleInvalidInputException(InvalidInputException ex, Locale locale){
		logger.error(ex.getLocalizedMessage(),ex);
		ResponseEntity<ApiError> response = getResponseEntity(ex,locale);
		logger.debug("error response generated.");
		return response;
	}
	
	private ResponseEntity<ApiError> getResponseEntity(ApiException ex, Locale locale){
		ApiError error = new ApiError();
		error.setApiErrorCode(ex.getErrorCode())
		.setHttpStatus(ex.getErrorCode().getHttpStatus().value())
		.setDeveloperMessage(ex.getLocalizedMessage())
		.setUserMessage(messageSource.getMessage(ex.getErrorCode().getName(),null,locale));
		
		return ResponseEntity.status(ex.getErrorCode().getHttpStatus())
				.body(error);
	}
}
