package com.company.pratica04.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.aluno.AlunoPostForm;
import com.company.pratica04.dto.aluno.AlunoPatchForm;
import com.company.pratica04.exception.DomainException;
import com.company.pratica04.mapper.AlunoMapper;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Turma;
import com.company.pratica04.repository.AlunoRepository;
import com.company.pratica04.repository.TurmaRepository;
import com.company.pratica04.service.AlunoService;

@Repository
public class AlunoServiceImpl implements AlunoService{

	@Autowired
	private AlunoRepository alunoRep;
	@Autowired
	private TurmaRepository turmaRep;

	private AlunoMapper mapper = AlunoMapper.INSTANCE;

	@Override
	public AlunoDto cadastra(AlunoPostForm form) {
		Optional<Turma> optTurma = turmaRep.findById(form.getIdTurma());		
		if (optTurma.isEmpty())
			throw new DomainException("Não existe uma turma com o id: " + form.getIdTurma(), HttpStatus.BAD_REQUEST);
		
		Optional<Aluno> optAluno = alunoRep.findByMatricula(form.getMatricula());
		if (optAluno.isPresent())
			throw new DomainException("A matrícula "+form.getMatricula()+" já foi cadastrada.", HttpStatus.BAD_REQUEST);
		
		Turma turma = optTurma.get();
		turma.setQuantidadeAlunos(turma.getQuantidadeAlunos() + 1);
		
		Aluno aluno = mapper.toAluno(form);
		aluno.setTurma(turma);
		aluno = alunoRep.save(aluno);
		return mapper.toDto(aluno);
	}

	@Override
	public Page<AlunoDto> buscaTodos(Pageable pageable) {
		return alunoRep.findAll(pageable).map(a -> mapper.toDto(a));
	}
	
	@Override
	public AlunoDto buscaPorId(Long id) {
		Aluno aluno = garanteQueAlunoExiste(id);
		return mapper.toDto(aluno);
	}
	
	@Override
	public void deleta(Long id) {
		Aluno aluno = garanteQueAlunoExiste(id);
		Turma turma = aluno.getTurma();
		if(turma != null)
			turma.setQuantidadeAlunos(turma.getQuantidadeAlunos()-1);
		
		alunoRep.delete(aluno);
	}
	
	@Override
	public AlunoDto atualiza(Long id, AlunoPatchForm form) {
		Aluno aluno = garanteQueAlunoExiste(id);
		
		Optional<Aluno> optAluno = alunoRep.findByMatricula(form.getMatricula());
		if(optAluno.isPresent() && !optAluno.get().getId().equals(id))
			throw new DomainException("A matrícula "+form.getMatricula()+" já foi cadastrada.", HttpStatus.BAD_REQUEST);
		
		aluno.setNome(form.getNome());
		aluno.setSobrenome(form.getSobrenome());
		aluno.setMatricula(form.getMatricula());
		aluno = alunoRep.save(aluno);
		
		return mapper.toDto(aluno);
	}
	
	public Aluno garanteQueAlunoExiste(Long id) {		
		Optional<Aluno> optAluno = alunoRep.findById(id);
		if(optAluno.isEmpty())
			throw new DomainException("Não existe um aluno com o id: " + id, HttpStatus.NOT_FOUND);
		
		return optAluno.get();
	}
}
