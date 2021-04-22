package com.company.pratica04.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @NoArgsConstructor
@ApiModel("ExceptionResponse")
public class ExceptionResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	@JsonProperty("timestamp")
	private LocalDateTime timestamp;
	@JsonProperty("status")
	private int status;
	@JsonProperty("error")
	private String error;
	@JsonProperty("message")
	private String message;
	@JsonProperty("path")
	private String path;
	
	/**
	 * Get timestamp
	 * @return timestamp
	**/
	@ApiModelProperty(value = "")
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	   * Get status
	   * @return status
	**/
	@ApiModelProperty(value = "")
	public int getStatus() {
		return status;
	}

	/**
	   * Get error
	   * @return error
	**/
	@ApiModelProperty(value = "")
	public String getError() {
		return error;
	}

	/**
	   * Get message
	   * @return message
	**/
	@ApiModelProperty(value = "")
	public String getMessage() {
		return message;
	}
	
	/**
	  * Get path
	  * @return path
	**/
	@ApiModelProperty(value = "")
	public String getPath() {
		return path;
	}


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
