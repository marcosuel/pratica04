package com.company.pratica04.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<AlunoDto> cadastra(@Valid @RequestBody AlunoForm form){
		AlunoDto dto = service.cadastra(form);
		return new ResponseEntity<AlunoDto>(dto, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<?> buscaTodos(Pageable pageable){
		return ResponseEntity.ok(service.buscaTodos(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscaPorId(@PathVariable Long id){
		return ResponseEntity.ok(service.buscaPorId(id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleta(@PathVariable Long id){
		service.deleta(id);
		return ResponseEntity.noContent().build();
	}
}
