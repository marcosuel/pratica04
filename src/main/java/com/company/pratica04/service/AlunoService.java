package com.company.pratica04.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.aluno.AlunoForm;
import com.company.pratica04.exception.DomainException;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Turma;
import com.company.pratica04.repository.AlunoRepository;
import com.company.pratica04.repository.TurmaRepository;

@Repository
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRep;
	@Autowired
	private TurmaRepository turmaRep;

	public AlunoDto save(AlunoForm form) {
		Optional<Turma> optTurma = turmaRep.findById(form.getIdTurma());		
		if (optTurma.isEmpty())
			throw new DomainException("Não existe uma turma com o id: " + form.getIdTurma(), HttpStatus.BAD_REQUEST);
		
		Optional<Aluno> optAluno = alunoRep.findByMatricula(form.getMatricula());
		if (optAluno.isPresent())
			throw new DomainException("A matrícula "+form.getMatricula()+" já foi cadastrada.", HttpStatus.BAD_REQUEST);
		
		Turma turma = optTurma.get();
		turma.setQuantidadeAlunos(turma.getQuantidadeAlunos() + 1);
		form.setTurma(turma);
		Aluno aluno = form.convert();
		
		aluno = alunoRep.save(aluno);
		return new AlunoDto(aluno);
	}

	public Page<AlunoDto> findAll(Pageable pageable) {
		return alunoRep.findAll(pageable).map(a -> new AlunoDto(a));
	}
	
	public AlunoDto findOne(Long id) {
		Optional<Aluno> optAluno = alunoRep.findById(id);
		if(optAluno.isEmpty())
			throw new DomainException("Não existe um aluno com o id: " + id, HttpStatus.NOT_FOUND);
		
		return new AlunoDto(optAluno.get());
	}
	
	public void delete(Long id) {
		Optional<Aluno> optAluno = alunoRep.findById(id);
		if(optAluno.isEmpty())
			throw new DomainException("Não existe um aluno com o id: " + id, HttpStatus.NOT_FOUND);
		
		Aluno aluno = optAluno.get();
		
		Turma turma = aluno.getTurma();
		turma.setQuantidadeAlunos(turma.getQuantidadeAlunos()-1);
		
		alunoRep.encerrarMentoria(id);
		alunoRep.delete(aluno);
	}
}
