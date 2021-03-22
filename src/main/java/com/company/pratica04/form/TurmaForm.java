package com.company.pratica04.form;

import java.time.Year;

import com.company.pratica04.model.Turma;

public class TurmaForm {

	private String nome;
	private Year anoLetivo;
	
	public TurmaForm() {}

	public TurmaForm(String nome, Year anoLetivo) {
		this.nome = nome;
		this.anoLetivo = anoLetivo;
	}
	
	public Turma convert() {
		return new Turma(nome, 0, anoLetivo);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Year getAnoLetivo() {
		return anoLetivo;
	}

	public void setAnoLetivo(Year anoLetivo) {
		this.anoLetivo = anoLetivo;
	}
}
