package com.core.ms.exception;

public class OrderNotFoundException extends RuntimeException {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException (String id){
        super("Could not find order " + id);
    }   
}