package com.company.pratica04.dto;

import java.time.Year;
import java.util.List;

import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Mentor;
import com.company.pratica04.model.Turma;

public class TurmaDto {
	private Long id;
	private String nome;
	private int quantidadeAlunos;
	private Year anoLetivo;
	private List<Mentor> mentores;
	private List<Aluno> alunos;
	
	public TurmaDto() {}

	public TurmaDto(Turma turma) {
		this.id = turma.getId();
		this.nome = turma.getNome();
		this.quantidadeAlunos = turma.getQuantidadeAlunos();
		this.anoLetivo = turma.getAnoLetivo();
		this.mentores = turma.getMentores();
		this.alunos = turma.getAlunos();
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

	public List<Mentor> getMentores() {
		return mentores;
	}

	public void setMentores(List<Mentor> mentores) {
		this.mentores = mentores;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
}
