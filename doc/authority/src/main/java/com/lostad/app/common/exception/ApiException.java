package com.lostad.app.common.exception;

/**
 * 处理service抛出运行时异常处理
 * @author lance
 */
public class ApiException extends RuntimeException {
	private static final long serialVersionUID = 1389958090308317369L;

	public ApiException() {
		super();
	}

	public ApiException(String msg, Throwable clause) {
		super(msg, clause);
	}

	public ApiException(String msg) {
		super(msg);
	}

	public ApiException(Throwable clause) {
		super(clause);
	}
	
}
