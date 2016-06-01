package com.keke.bean;

import java.io.Serializable;

import com.google.gson.Gson;



public class ErrorCode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -276029364179568891L;
	//返回的错误码
	private String errorCode;
	//返回的数据
	private String errorMsg;
	
	
	public ErrorCode() {
		super();
		
	}

	public ErrorCode(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
