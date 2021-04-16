package com.company.pratica04.dto.turma;

import java.time.Year;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TurmaPostForm {
	@NotBlank
	private String nome;
	@NotNull
	private Year anoLetivo;
}
