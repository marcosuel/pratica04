package com.company.pratica04.dto.aluno;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AlunoPostForm {

	@NotBlank
	private String nome;
	@NotBlank
	private String sobrenome;
	@NotNull
	private Long matricula;
	@NotNull
	private Long idTurma;
	
	public AlunoPostForm() {}

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
