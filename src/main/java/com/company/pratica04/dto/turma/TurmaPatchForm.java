package com.company.pratica04.dto.turma;

import java.time.Year;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.company.pratica04.model.Turma;

public class TurmaPatchForm {

	@NotBlank
	private String nome;
	@NotNull
	private Year anoLetivo;
	
	public TurmaPatchForm() {}

	public TurmaPatchForm(String nome, Year anoLetivo) {
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
