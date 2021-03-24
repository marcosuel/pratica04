package com.company.pratica04.dto.mentor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.company.pratica04.model.Mentor;

public class MentorForm {

	@NotBlank
	private String nome;
	@NotBlank
	private String sobrenome;
	@NotNull
	private Long matricula;
	
	public MentorForm() {}
	
	public MentorForm(String nome, String sobrenome, Long matricula) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.matricula = matricula;
	}
	
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
