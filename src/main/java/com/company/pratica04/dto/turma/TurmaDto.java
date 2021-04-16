package com.company.pratica04.dto.turma;

import java.time.Year;
import java.util.List;

import com.company.pratica04.dto.aluno.AlunoItemListaTurmaDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TurmaDto {
	private Long id;
	private String nome;
	private int quantidadeAlunos;
	private Year anoLetivo;
	private List<AlunoItemListaTurmaDto> alunos;
}
