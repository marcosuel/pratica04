package com.company.pratica04.dto.mentor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.company.pratica04.model.Mentor;

public class MentorPostForm {

	@NotBlank
	private String nome;
	@NotBlank
	private String sobrenome;
	@NotNull
	private Long matricula;
	
	public MentorPostForm() {}
	
	public Mentor convert() {
		return new Mentor(nome, sobrenome, matricula);
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
