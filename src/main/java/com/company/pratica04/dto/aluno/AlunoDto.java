package com.company.pratica04.dto.aluno;

import com.company.pratica04.dto.turma.TurmaItemAlunoDto;

public class AlunoDto {

	private Long id;
	private String nome;
	private String sobrenome;
	private Long matricula;
	private TurmaItemAlunoDto turma;
	
	public AlunoDto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public TurmaItemAlunoDto getTurma() {
		return turma;
	}

	public void setTurma(TurmaItemAlunoDto turma) {
		this.turma = turma;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
}
