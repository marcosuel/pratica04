package com.company.pratica04.dto.mentor;

import com.company.pratica04.model.Mentor;

public class ListaMentorDto {
	
	private Long id;
	private String nomeCompleto;
	private Long matricula;
	
	
	public ListaMentorDto() {}

	public ListaMentorDto(Mentor mentor) {
		this.id = mentor.getId();
		this.nomeCompleto = mentor.getNome()+" "+mentor.getSobrenome();
		this.matricula = mentor.getMatricula();
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

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
}
