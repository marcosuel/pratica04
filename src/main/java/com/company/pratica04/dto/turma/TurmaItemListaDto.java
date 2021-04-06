package com.company.pratica04.dto.turma;

import java.time.Year;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TurmaItemListaDto {
	private Long id;
	private String nome;
	private int quantidadeAlunos;
	private Year anoLetivo;
}
