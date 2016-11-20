package com.demo.steel.api;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ApiError> handleInvalidInputException(InvalidInputException ex, Locale locale){
		logger.error(ex.getMessage(),ex);
		ResponseEntity<ApiError> response = getResponseEntity(ex,locale);
		logger.debug("error response generated.");
		return response;
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiError> handleMysqlSyntaxException(RuntimeException ex, Locale locale){
		logger.error(ex.getMessage(),ex);
		ApiError error = getApiError(ErrorCode.INTERNAL_ERROR,ex, locale);
		ResponseEntity<ApiError> response = getResponseEntity(error);
		logger.debug("error response generated.");
		return response;
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex, Locale locale){
		logger.error(ex.getMessage(),ex);
		ApiError error = getApiError(ErrorCode.ACCESS_DENIED, ex, locale);
		ResponseEntity<ApiError> response = getResponseEntity(error);
		return response;
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiError> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, Locale locale){
		logger.error(ex.getMessage(), ex);
		ApiError error = new ApiError();
		error.setApiErrorCode(ErrorCode.MISSING_REQ_PARAM)
		.setDeveloperMessage(ex.getMessage())
		.setHttpStatus(HttpStatus.BAD_REQUEST.value())
		.setUserMessage(ex.getMessage());
		ResponseEntity<ApiError> response = getResponseEntity(error);
		logger.debug("error response generated.");
		return response;
	}
	
	private ResponseEntity<ApiError> getResponseEntity(ApiError error){
		return ResponseEntity.status(error.getHttpStatus())
				.body(error);
	}
	
	private ResponseEntity<ApiError> getResponseEntity(ApiException ex, Locale locale){
		ApiError error = new ApiError();
		error.setApiErrorCode(ex.getErrorCode())
		.setHttpStatus(ex.getErrorCode().getHttpStatus().value())
		.setDeveloperMessage(ex.getMessage());
		
		try {
			error.setUserMessage(messageSource.getMessage(ex.getErrorCode().getName(),null,locale));
		} catch (NoSuchMessageException e) {
			logger.error(e.getMessage(),e);
			error = getApiError(ErrorCode.INTERNAL_ERROR,e,locale);
		}
		
		return ResponseEntity.status(error.getHttpStatus())
				.body(error);
	}
	
	private ApiError getApiError(ErrorCode code,Exception e,Locale locale){
		ApiError error = new ApiError();
		error.setApiErrorCode(code)
		.setHttpStatus(code.getHttpStatus().value())
		.setDeveloperMessage(e.getMessage())
		.setUserMessage(messageSource.getMessage(code.getName(),null, locale));
	
		return error;
	}
	
}
