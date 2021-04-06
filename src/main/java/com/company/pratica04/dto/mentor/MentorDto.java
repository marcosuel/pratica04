package com.company.pratica04.dto.mentor;

import java.util.List;

import com.company.pratica04.dto.aluno.AlunoDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MentorDto {
	private Long id;
	private String nome;
	private String sobrenome;
	private Long matricula;
	private List<AlunoDto> mentorados;
}
