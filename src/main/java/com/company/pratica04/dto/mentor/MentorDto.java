package com.company.pratica04.dto.mentor;

import java.util.List;

import com.company.pratica04.dto.aluno.AlunoDto;

public class MentorDto {
	
	private Long id;
	private String nome;
	private String sobrenome;
	private Long matricula;
	private List<AlunoDto> mentorados;
	
	
	public MentorDto() {}

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

	public List<AlunoDto> getMentorados() {
		return mentorados;
	}

	public void setMentorados(List<AlunoDto> mentorados) {
		this.mentorados = mentorados;
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
