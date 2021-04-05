package com.company.pratica04.dto.turma;

import java.time.Year;

public class TurmaItemAlunoDto {
	private Long id;
	private String nome;
	private Year anoLetivo;
	
	public TurmaItemAlunoDto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
