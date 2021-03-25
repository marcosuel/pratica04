package com.company.pratica04.dto.aluno;

import com.company.pratica04.model.Aluno;

public class ListaAlunoTurmaDto {

	private Long id;
	private String nomeCompleto;
	private Long matricula;
	
	public ListaAlunoTurmaDto() {}

	public ListaAlunoTurmaDto(Aluno aluno) {
		this.id = aluno.getId();
		this.nomeCompleto = aluno.getNome()+" "+aluno.getSobrenome();
		this.matricula = aluno.getMatricula();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}
}
