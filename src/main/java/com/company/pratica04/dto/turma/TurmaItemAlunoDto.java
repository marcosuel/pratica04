package com.company.pratica04.dto.turma;

import java.time.Year;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class TurmaItemAlunoDto {
	private Long id;
	private String nome;
	private Year anoLetivo;
}
