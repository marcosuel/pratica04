package com.company.pratica04.dto.aluno;

import com.company.pratica04.dto.turma.TurmaItemAlunoDto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor @ToString
public class AlunoDto {
	private Long id;
	private String nome;
	private String sobrenome;
	private Long matricula;
	private TurmaItemAlunoDto turma;
}
