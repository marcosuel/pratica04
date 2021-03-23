package com.company.pratica04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.company.pratica04.dto.turma.TurmaDto;
import com.company.pratica04.dto.turma.TurmaForm;
import com.company.pratica04.model.Turma;
import com.company.pratica04.repository.TurmaRepository;

@Service
public class TurmaService {

	@Autowired
	private TurmaRepository repository;
	
	public TurmaDto save(TurmaForm form) {
		Turma turma = form.convert();
		turma = repository.save(turma);
		
		return new TurmaDto(turma);
	}
	
	public Page<TurmaDto> findAll(Pageable pageable) {
		return repository.findAll(pageable).map(t -> new TurmaDto(t));
	}
}
