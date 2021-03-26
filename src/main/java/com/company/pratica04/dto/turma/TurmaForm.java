package com.company.pratica04.dto.turma;

import java.time.Year;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.company.pratica04.model.Turma;

public class TurmaForm {

	@NotBlank
	private String nome;
	@NotNull @Size(min = 2000, max = 2100)
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
