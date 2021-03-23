package com.company.pratica04.dto.aluno;

import com.company.pratica04.dto.turma.TurmaDto;
import com.company.pratica04.model.Aluno;

public class AlunoDto {

	private Long id;
	private String nomeCompleto;
	private Long matricula;
	private TurmaDto turma;
	
	public AlunoDto() {}

	public AlunoDto(Aluno aluno) {
		this.id = aluno.getId();
		this.nomeCompleto = aluno.getNome()+" "+aluno.getSobrenome();
		this.matricula = aluno.getMatricula();
		this.turma = isTurmaPresent(aluno) ? new TurmaDto(aluno.getTurma()) : null;
	}
	
	private boolean isTurmaPresent(Aluno aluno) {
		return aluno.getTurma() == null ? false : true;
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

	public TurmaDto getTurma() {
		return turma;
	}

	public void setTurma(TurmaDto turma) {
		this.turma = turma;
	}
}
