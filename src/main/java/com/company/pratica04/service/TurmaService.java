package com.company.pratica04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.pratica04.dto.TurmaDto;
import com.company.pratica04.form.TurmaForm;
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
	
}
