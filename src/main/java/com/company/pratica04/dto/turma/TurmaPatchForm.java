package com.company.pratica04.dto.turma;

import java.time.Year;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class TurmaPatchForm {
	@NotBlank
	private String nome;
	@NotNull
	private Year anoLetivo;
}
