package com.keke.exception;


public class OrderErrorViewException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2290340979915613994L;

	private boolean success;
	private Object data;
	private String errorCode;

	public OrderErrorViewException(boolean success, Object data,String errorCode) {
		super();
		this.success = success;
		this.data = data;
		this.errorCode = errorCode;

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	

}
