package com.gustavosass.finance.exceptions;

public class FoundItemsPaidForTransactionException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public FoundItemsPaidForTransactionException(String msg) {
        super(msg);
    }
}
