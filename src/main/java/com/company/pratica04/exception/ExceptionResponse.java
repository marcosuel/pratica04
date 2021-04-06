package com.company.pratica04.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ExceptionResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
	
	public static final class ExceptionResponseBuilder {
		private HttpStatus status;
		private String error;
		private String message;
		private String path;
		
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
		
		public ExceptionResponseBuilder withPath(String path) {
			this.path = path;
			return this;
		}
		
		public ExceptionResponse build() {
			ExceptionResponse exceptionResponse = new ExceptionResponse();
			exceptionResponse.status = this.status.value();
			exceptionResponse.error = this.error;
			exceptionResponse.message = this.message;
			exceptionResponse.timestamp = LocalDateTime.now();
			exceptionResponse.path = this.path;
			return exceptionResponse;
		}
	}
}
