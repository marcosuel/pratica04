package com.company.pratica04.model;

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

@Entity(name = "mentor")
@Data @NoArgsConstructor @AllArgsConstructor
public class Mentor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String sobrenome;
	private Long matricula;
	@OneToMany
	@JoinColumn(name = "mentor_id")
	private List<Aluno> mentorados = new ArrayList<Aluno>();
}
