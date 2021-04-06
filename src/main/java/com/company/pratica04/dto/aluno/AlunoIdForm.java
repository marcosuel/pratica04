package com.company.pratica04.dto.aluno;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class AlunoIdForm {
	@NotNull 
	private Long idAluno;
}
