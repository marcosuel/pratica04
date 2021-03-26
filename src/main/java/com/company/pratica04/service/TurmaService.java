package com.company.pratica04.service;

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
import com.company.pratica04.model.Turma;
import com.company.pratica04.repository.TurmaRepository;

@Service
public class TurmaService {

	@Autowired
	private TurmaRepository repository;
	
	public TurmaDto cadastra(TurmaForm form) {
		Turma turma = form.convert();
		turma = repository.save(turma);
		
		return new TurmaDto(turma);
	}
	
	public Page<TurmaItemListaDto> buscaTodos(Pageable pageable) {
		return repository.findAll(pageable).map(t -> new TurmaItemListaDto(t));
	}
	
	public TurmaDto buscaPorId(Long id) {
		Turma turma = garanteQueTurmaExiste(id);
		return new TurmaDto(turma);
	}
	
	public void deletaPorId(Long id) {
		if(!repository.existsById(id))	lancaDomainException(id);
		repository.deleteById(id);
	}
	
	private Turma garanteQueTurmaExiste(Long id) {
		Optional<Turma> optTurma = repository.findById(id);
		if(optTurma.isEmpty())
			lancaDomainException(id);
		return optTurma.get();
	}
	
	private void lancaDomainException(Long id) {
		throw new DomainException("Não foi encontrada uma turma com id: "+id, HttpStatus.NOT_FOUND);
	}
}
