package com.company.pratica04.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "aluno")
@Data  @NoArgsConstructor @AllArgsConstructor
public class Aluno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String sobrenome;
	private Long matricula;
	@ManyToOne
	@JoinColumn(name = "turma_id")
	private Turma turma;
	@ManyToOne
	private Mentor mentor;
	
	public Aluno(String nome, String sobrenome, Long matricula, Turma turma) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.matricula = matricula;
		this.turma = turma;
	}
}
