package com.company.pratica04.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FormFieldResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String status;
	private List<FormFieldError> erros;
	private String path;
	
	public FormFieldResponse(String status, List<FormFieldError> erros, String path) {
		this.timestamp = LocalDateTime.now();
		this.status = status;
		this.erros = erros;
		this.path = path;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<FormFieldError> getErros() {
		return erros;
	}

	public void setErros(List<FormFieldError> erros) {
		this.erros = erros;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
