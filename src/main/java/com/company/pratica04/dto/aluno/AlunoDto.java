package com.company.pratica04.dto.aluno;

import com.company.pratica04.dto.turma.TurmaItemAlunoDto;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Turma;

public class AlunoDto {

	private Long id;
	private String nomeCompleto;
	private Long matricula;
	private TurmaItemAlunoDto turma;
	
	public AlunoDto() {}

	public AlunoDto(Aluno aluno) {
		this.id = aluno.getId();
		this.nomeCompleto = aluno.getNome()+" "+aluno.getSobrenome();
		this.matricula = aluno.getMatricula();
		Turma turma = aluno.getTurma();
		this.turma = turma != null ? new TurmaItemAlunoDto(turma) : null;
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

	public TurmaItemAlunoDto getTurma() {
		return turma;
	}

	public void setTurma(TurmaItemAlunoDto turma) {
		this.turma = turma;
	}
}
