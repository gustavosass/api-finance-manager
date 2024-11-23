package com.gustavosass.finance.exceptions;

public class ObjectAlreadyExistsException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public ObjectAlreadyExistsException(String msg) {
        super(msg);
    }
}
