package com.company.pratica04.dto.aluno;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class AlunoItemListaTurmaDto {
	private Long id;
	private String nome;
	private String sobrenome;
	private Long matricula;
}
