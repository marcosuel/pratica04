package com.company.pratica04.exception;

import org.springframework.http.HttpStatus;

public class DomainException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String message;
	private final HttpStatus status;
	
	public DomainException(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
}
