package com.company.pratica04.dto.turma;

import java.time.Year;
import java.util.List;

import com.company.pratica04.dto.aluno.AlunoItemListaTurmaDto;

public class TurmaDto {
	private Long id;
	private String nome;
	private int quantidadeAlunos;
	private Year anoLetivo;
	private List<AlunoItemListaTurmaDto> alunos;
	
	public TurmaDto() {}

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

	public int getQuantidadeAlunos() {
		return quantidadeAlunos;
	}

	public void setQuantidadeAlunos(int quantidadeAlunos) {
		this.quantidadeAlunos = quantidadeAlunos;
	}

	public Year getAnoLetivo() {
		return anoLetivo;
	}

	public void setAnoLetivo(Year anoLetivo) {
		this.anoLetivo = anoLetivo;
	}

	public List<AlunoItemListaTurmaDto> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<AlunoItemListaTurmaDto> alunos) {
		this.alunos = alunos;
	}
}
