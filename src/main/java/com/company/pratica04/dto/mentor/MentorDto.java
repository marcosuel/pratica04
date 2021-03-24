package com.company.pratica04.dto.mentor;

import java.util.List;
import java.util.stream.Collectors;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.model.Mentor;

public class MentorDto {
	
	private Long id;
	private String nomeCompleto;
	private Long matricula;
	private List<AlunoDto> mentorados;
	
	
	public MentorDto() {}

	public MentorDto(Mentor mentor) {
		this.id = mentor.getId();
		this.nomeCompleto = mentor.getNome()+" "+mentor.getSobrenome();
		this.matricula = mentor.getMatricula();
		this.mentorados = mentor.getMentorados()
							.stream().map(a -> new AlunoDto(a))
							.collect(Collectors.toList());
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

	public List<AlunoDto> getMentorados() {
		return mentorados;
	}

	public void setMentorados(List<AlunoDto> mentorados) {
		this.mentorados = mentorados;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
}
