package com.company.pratica04.dto.turma;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

import com.company.pratica04.dto.aluno.ItemListaAlunoTurmaDto;
import com.company.pratica04.model.Turma;

public class TurmaDto {
	private Long id;
	private String nome;
	private int quantidadeAlunos;
	private Year anoLetivo;
	private List<ItemListaAlunoTurmaDto> alunos;
	
	public TurmaDto() {}

	public TurmaDto(Turma turma) {
		this.id = turma.getId();
		this.nome = turma.getNome();
		this.quantidadeAlunos = turma.getQuantidadeAlunos();
		this.anoLetivo = turma.getAnoLetivo();
		this.alunos = turma.getAlunos().stream().map(a -> new ItemListaAlunoTurmaDto(a)).collect(Collectors.toList());
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

	public Year getAnoLetivo() {
		return anoLetivo;
	}

	public void setAnoLetivo(Year anoLetivo) {
		this.anoLetivo = anoLetivo;
	}

	public List<ItemListaAlunoTurmaDto> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<ItemListaAlunoTurmaDto> alunos) {
		this.alunos = alunos;
	}
}
