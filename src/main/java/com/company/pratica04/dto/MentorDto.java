package com.company.pratica04.dto;

import java.util.List;

import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Mentor;
import com.company.pratica04.model.Turma;

public class MentorDto {
	
	private Long id;
	private String nomeCompleto;
	private Long matricula;
	private List<Turma> turmas;
	private List<Aluno> mentorados;
	
	
	public MentorDto() {}

	public MentorDto(Mentor mentor) {
		this.id = mentor.getId();
		this.nomeCompleto = mentor.getNome()+" "+mentor.getSobrenome();
		this.matricula = mentor.getMatricula();
		this.turmas = mentor.getTurmas();
		this.mentorados = mentor.getMentorados();
	}

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

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public List<Aluno> getMentorados() {
		return mentorados;
	}

	public void setMentorados(List<Aluno> mentorados) {
		this.mentorados = mentorados;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
}
