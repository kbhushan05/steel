package com.demo.steel.api;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ApiError> handleInvalidInputException(InvalidInputException ex, Locale locale){
		return generateErrorResponse(ex, locale);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex, Locale locale){
		return generateErrorResponse(ErrorCode.INTERNAL_ERROR, ex, locale);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError[]> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale){
		logger.error(ex.getMessage(), ex);
		int errorCount = ex.getBindingResult().getErrorCount();
		List<ObjectError> globalErrors = ex.getBindingResult().getAllErrors();
		ApiError errors[] = new ApiError[errorCount];
		for(int i = 0; i < errorCount; i++){
			ObjectError error = globalErrors.get(i);
			errors[i] = getApiError(ErrorCode.BAD_INPUT, ex, locale);
			errors[i].setDeveloperMessage(error.toString());
			errors[i].setUserMessage(error.getDefaultMessage());
		}
		logger.debug("error response generated.");
		return getResponseEntity(errors);
	}
	
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex, Locale locale){
		return generateErrorResponse(ErrorCode.ACCESS_DENIED, ex, locale);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiError> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, Locale locale){
		return generateErrorResponse(ErrorCode.MISSING_REQ_PARAM, ex, locale);
	}
	
	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity<ApiError> handleJsonParseException(JsonParseException ex, Locale locale){
		return generateErrorResponse(ErrorCode.BAD_INPUT, ex, locale);
	}
	
	private ResponseEntity<ApiError> generateErrorResponse(ErrorCode errorCode, Exception ex, Locale locale){
		logger.error(ex.getMessage(), ex);
		ApiError error = getApiError(errorCode, ex, locale);
		ResponseEntity<ApiError> response = getResponseEntity(error);
		logger.debug("error response generated.");
		return response;
	}
	
	private ResponseEntity<ApiError> generateErrorResponse(ApiException ex, Locale locale){
		return generateErrorResponse(ex.getErrorCode(), ex, locale);
	}
	
	private ResponseEntity<ApiError> getResponseEntity(ApiError error){
		return ResponseEntity.status(error.getHttpStatus())
				.body(error);
	}
	
	private ResponseEntity<ApiError[]> getResponseEntity(ApiError[] errors){
		return ResponseEntity.status(errors[0].getHttpStatus())
				.body(errors);
	}
	
	private ApiError getApiError(ErrorCode errorCode,Exception ex,Locale locale){
		ApiError error = new ApiError();
		error.setApiErrorCode(errorCode)
		.setHttpStatus(errorCode.getHttpStatus().value())
		.setDeveloperMessage(ex.getMessage());
		
		try {
			error.setUserMessage(messageSource.getMessage(errorCode.getName(),null,locale));
		} catch (NoSuchMessageException e) {
			logger.error(e.getMessage(),e);
			error = getApiError(ErrorCode.INTERNAL_ERROR,e,locale);
		}
	
		return error;
	}
	
}
