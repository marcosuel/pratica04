package com.company.pratica04.dto.turma;

import java.time.Year;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class TurmaPostForm {
	@NotBlank
	private String nome;
	@NotNull
	private Year anoLetivo;
}
