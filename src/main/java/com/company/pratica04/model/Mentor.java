package com.company.pratica04.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity(name = "mentor")
public class Mentor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String sobrenome;
	private Long matricula;
	@OneToMany
	@JoinTable(name = "tb_mentoria")
	private List<Aluno> mentorados;
	
	public Mentor() {}

	public Mentor(String nome, String sobrenome, Long matricula) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.matricula = matricula;
		this.mentorados = new ArrayList<Aluno>();
	}

	public Mentor(Long id, String nome, String sobrenome, Long matricula, List<Aluno> mentorados) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.matricula = matricula;
		this.mentorados = mentorados;
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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public List<Aluno> getMentorados() {
		return mentorados;
	}

	public void setMentorados(List<Aluno> mentorados) {
		this.mentorados = mentorados;
	}
}
