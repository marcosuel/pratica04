package com.company.pratica04.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "turma")
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Turma {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private int quantidadeAlunos;
	private Year anoLetivo;
	@OneToMany
	@JoinColumn(name = "turma_id")
	private List<Aluno> alunos = new ArrayList<Aluno>();
	
	public Turma(Long id, String nome, int quantidadeAlunos, Year anoLetivo) {
		this.id = id;
		this.nome = nome;
		this.quantidadeAlunos = quantidadeAlunos;
		this.anoLetivo = anoLetivo;
	}
}
