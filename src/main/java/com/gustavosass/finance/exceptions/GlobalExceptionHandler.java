package com.gustavosass.finance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ExceptionResponse> handleAllExceptions(Throwable throwable, WebRequest webRequest) {
		return new ResponseEntity<>(newExceptionResponse(throwable, webRequest), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ExceptionResponse newExceptionResponse(Throwable throwable, WebRequest webRequest) {
		return new ExceptionResponse(throwable.getMessage(), webRequest.getDescription(false));
	}

}