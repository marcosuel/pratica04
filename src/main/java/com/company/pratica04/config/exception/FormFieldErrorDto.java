package com.company.pratica04.config.exception;

public class FormFieldErrorDto {

	private String campo;
	private String erro;
	
	public FormFieldErrorDto(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}
	
}
