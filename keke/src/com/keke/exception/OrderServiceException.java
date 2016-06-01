package com.keke.exception;

public class OrderServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2290340979915613994L;

	public OrderServiceException() {
		super();
	
	}

	public OrderServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public OrderServiceException(String message) {
		super(message);
		
	}

	public OrderServiceException(Throwable cause) {
		super(cause);
		
	}
	
}
