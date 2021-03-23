package com.company.pratica04.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.company.pratica04.dto.AlunoDto;
import com.company.pratica04.form.AlunoForm;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.repository.AlunoRepository;

@Repository
public class AlunoService {

	@Autowired
	private AlunoRepository repository;
	
	
	public AlunoDto save(AlunoForm form) {
		Aluno aluno = form.convert();
		aluno = repository.save(aluno);
		return new AlunoDto(aluno);
	}
	
	public Page<AlunoDto> findAll(Pageable pageable){
		return repository.findAll(pageable).map(a -> new AlunoDto(a));
	}
}
