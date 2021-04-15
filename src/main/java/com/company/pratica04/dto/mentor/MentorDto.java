package com.company.pratica04.dto.mentor;

import java.util.ArrayList;
import java.util.List;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.model.Aluno;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MentorDto {
	private Long id;
	private String nome;
	private String sobrenome;
	private Long matricula;
	private List<AlunoDto> mentorados;
	
	public MentorDto(Long id, String nome, String sobrenome, Long matricula) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.matricula = matricula;
		mentorados = new ArrayList<AlunoDto>();
	}
}
