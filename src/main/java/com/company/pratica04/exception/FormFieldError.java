package com.company.pratica04.exception;

public class FormFieldError {

	private String campo;
	private String mensagem;

	public FormFieldError(String campo, String mensagem) {
		this.campo = campo;
		this.mensagem = mensagem;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return mensagem;
	}
}