package com.company.pratica04.dto.aluno;

import javax.validation.constraints.NotNull;

public class AlunoIdForm {

	@NotNull
	private Long idAluno;

	public AlunoIdForm() {}
	
	public AlunoIdForm(Long idAluno) {
		this.idAluno = idAluno;
	}

	public Long getId() {
		return idAluno;
	}

	public Long getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}
	

}
