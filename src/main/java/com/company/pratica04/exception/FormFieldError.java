package com.company.pratica04.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class FormFieldError {
	private String campo;
	private String mensagem;
}