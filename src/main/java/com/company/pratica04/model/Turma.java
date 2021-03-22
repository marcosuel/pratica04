package com.company.pratica04.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity(name = "turma")
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private int quantidadeAlunos;
	private Year anoLetivo;
	
	@ManyToMany
	@JoinTable(name = "mentor_turma")
	private List<Mentor> mentores;
	@OneToMany
	@JoinColumn(name = "turma_id")
	private List<Aluno> alunos;
	
	public Turma() {}

	public Turma(String nome, Integer quantidadeAlunos, Year anoLetivo) {
		this.nome = nome;
		this.quantidadeAlunos = quantidadeAlunos;
		this.anoLetivo = anoLetivo;
		this.mentores = new ArrayList<Mentor>();
		this.alunos = new ArrayList<Aluno>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidadeAlunos() {
		return quantidadeAlunos;
	}

	public void setQuantidadeAlunos(int quantidadeAlunos) {
		this.quantidadeAlunos = quantidadeAlunos;
	}

	public List<Mentor> getMentores() {
		return mentores;
	}

	public void setMentores(List<Mentor> mentores) {
		this.mentores = mentores;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Year getAnoLetivo() {
		return anoLetivo;
	}

	public void setAnoLetivo(Year anoLetivo) {
		this.anoLetivo = anoLetivo;
	}
}
