package com.gustavosass.finance.exceptions;

public class NullArgumentException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public NullArgumentException() {
        super("Error processing with null body.");
    }
}
