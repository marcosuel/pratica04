package com.company.pratica04.dto.aluno;

import com.company.pratica04.model.Aluno;

public class AlunoForm {

	private String nome;
	private String sobrenome;
	private Long matricula;
	
	public AlunoForm() {}

	public AlunoForm(String nome, String sobrenome, Long matricula) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.matricula = matricula;
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
}
