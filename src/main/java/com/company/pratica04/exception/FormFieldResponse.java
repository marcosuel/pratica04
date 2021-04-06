package com.company.pratica04.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
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
}
