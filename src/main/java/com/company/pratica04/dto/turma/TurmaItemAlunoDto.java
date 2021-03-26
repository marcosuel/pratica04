package com.company.pratica04.dto.turma;

import java.time.Year;

import com.company.pratica04.model.Turma;

public class TurmaItemAlunoDto {
	private Long id;
	private String nome;
	private Year anoLetivo;
	
	public TurmaItemAlunoDto() {}

	public TurmaItemAlunoDto(Turma turma) {
		this.id = turma.getId();
		this.nome = turma.getNome();
		this.anoLetivo = turma.getAnoLetivo();
	}

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
