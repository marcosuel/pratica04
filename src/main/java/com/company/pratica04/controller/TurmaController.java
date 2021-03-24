package com.company.pratica04.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.pratica04.dto.turma.TurmaDto;
import com.company.pratica04.dto.turma.TurmaForm;
import com.company.pratica04.service.TurmaService;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

	@Autowired
	private TurmaService service;
	
	@PostMapping
	public ResponseEntity<TurmaDto> save(@Valid @RequestBody TurmaForm form){
		TurmaDto dto = service.save(form);
		return new ResponseEntity<TurmaDto>(dto, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Page> findAll(Pageable pageable){
		return ResponseEntity.ok(service.findAll(pageable));
	}
}
