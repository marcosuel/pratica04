package com.company.pratica04.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.aluno.AlunoForm;
import com.company.pratica04.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
	
	@Autowired
	private AlunoService service;

	@PostMapping
	public ResponseEntity<AlunoDto> save(@Valid @RequestBody AlunoForm form){
		AlunoDto dto = service.save(form);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(Pageable pageable){
		return ResponseEntity.ok(service.findAll(pageable));
	}
	
}
