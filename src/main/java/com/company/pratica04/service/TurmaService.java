package com.company.pratica04.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.company.pratica04.dto.turma.TurmaItemListaDto;
import com.company.pratica04.dto.turma.TurmaDto;
import com.company.pratica04.dto.turma.TurmaForm;
import com.company.pratica04.exception.DomainException;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Turma;
import com.company.pratica04.repository.AlunoRepository;
import com.company.pratica04.repository.TurmaRepository;

@Service
public class TurmaService {

	@Autowired
	private TurmaRepository turmaRep;
	@Autowired
	private AlunoRepository alunoRep;
	
	public TurmaDto cadastra(TurmaForm form) {
		Turma turma = form.convert();
		turma = turmaRep.save(turma);
		
		return new TurmaDto(turma);
	}
	
	public Page<TurmaItemListaDto> buscaTodos(Pageable pageable) {
		return turmaRep.findAll(pageable).map(t -> new TurmaItemListaDto(t));
	}
	
	public TurmaDto buscaPorId(Long id) {
		Turma turma = garanteQueTurmaExiste(id);
		return new TurmaDto(turma);
	}
	
	public void deletaPorId(Long id) {
		if(!turmaRep.existsById(id))	lancaDomainException(id);
		turmaRep.deleteById(id);
	}
	
	public void removeAluno(Long idTurma, Long idAluno) {
		Turma turma = garanteQueTurmaExiste(idTurma);
		List<Aluno> alunos = turma.getAlunos();
		
		boolean alunoRemovido = alunos.removeIf(a -> a.getId().equals(idAluno));
		if(!alunoRemovido)
			throw new DomainException("O aluno com id "+idAluno+" não está na turma de id "
					+idTurma, HttpStatus.BAD_REQUEST);
		
		turmaRep.save(turma);
		alunoRep.encerrarMentoria(idAluno);
	}
	
	private Turma garanteQueTurmaExiste(Long id) {
		Optional<Turma> optTurma = turmaRep.findById(id);
		if(optTurma.isEmpty())
			lancaDomainException(id);
		return optTurma.get();
	}
	
	private void lancaDomainException(Long id) {
		throw new DomainException("Não foi encontrada uma turma com id: "+id, HttpStatus.NOT_FOUND);
	}
}
