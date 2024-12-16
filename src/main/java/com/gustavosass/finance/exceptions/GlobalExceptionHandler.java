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

	@ExceptionHandler(FoundItemsPaidForTransactionException.class)
	public ResponseEntity<ExceptionResponse> handleFoundItemsPaidForTransactionException(FoundItemsPaidForTransactionException foundItemsPaidForTransactionException, WebRequest webRequest){
		return new ResponseEntity<>(newExceptionResponse(foundItemsPaidForTransactionException, webRequest), HttpStatus.OK);
	}
	
	@ExceptionHandler(NullArgumentException.class)
	public ResponseEntity<ExceptionResponse> handleNullArgumentException(NullArgumentException nullArgumentException, WebRequest webRequest){
		return new ResponseEntity<>(newExceptionResponse(nullArgumentException, webRequest), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ObjectAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> handleObjectAlreadyExistsException(ObjectAlreadyExistsException objectAlreadyExistsException, WebRequest webRequest){
		return new ResponseEntity<>(newExceptionResponse(objectAlreadyExistsException, webRequest), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleObjectAlreadyExistsException(ObjectNotFoundException objectNotFoundException, WebRequest webRequest){
		return new ResponseEntity<>(newExceptionResponse(objectNotFoundException, webRequest), HttpStatus.NOT_FOUND);
	}
		
	private ExceptionResponse newExceptionResponse(Throwable throwable, WebRequest webRequest) {
		return new ExceptionResponse(throwable.getMessage(), webRequest.getDescription(false));
	}

}