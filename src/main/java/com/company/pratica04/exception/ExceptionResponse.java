package com.company.pratica04.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExceptionResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	
	public static final class ExceptionResponseBuilder {
		private HttpStatus status;
		private String error;
		private String message;
		
		private ExceptionResponseBuilder() {}
		
		public static ExceptionResponseBuilder create() {
			return new ExceptionResponseBuilder();
		}
		
		public ExceptionResponseBuilder withStatus(HttpStatus status) {
			this.status = status;
			return this;
		}
		
		public ExceptionResponseBuilder withError(String error) {
			this.error = error;
			return this;
		}
		
		public ExceptionResponseBuilder withMessage(String message) {
			this.message = message;
			return this;
		}
		
		public ExceptionResponse build() {
			ExceptionResponse exceptionResponse = new ExceptionResponse();
			exceptionResponse.status = this.status.value();
			exceptionResponse.error = this.error;
			exceptionResponse.message = this.message;
			exceptionResponse.timestamp = LocalDateTime.now();
			return exceptionResponse;
		}
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
