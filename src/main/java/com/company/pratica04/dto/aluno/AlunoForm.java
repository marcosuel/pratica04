package com.company.pratica04.dto.aluno;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Turma;

import springfox.documentation.annotations.ApiIgnore;

public class AlunoForm {

	@NotBlank
	private String nome;
	@NotBlank
	private String sobrenome;
	@NotNull
	private Long matricula;
	@NotNull
	private Long idTurma;
	
	public AlunoForm() {}

	public AlunoForm(String nome, String sobrenome, Long matricula, Long idTurma) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.matricula = matricula;
		this.idTurma = idTurma;
	}
	
	public Aluno convert() {
		return new Aluno(nome, sobrenome, matricula);
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

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public Long getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(Long idTurma) {
		this.idTurma = idTurma;
	}
}
