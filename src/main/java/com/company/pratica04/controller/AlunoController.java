package com.company.pratica04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.pratica04.dto.AlunoDto;
import com.company.pratica04.form.AlunoForm;
import com.company.pratica04.service.AlunoService;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
	
	@Autowired
	private AlunoService service;

	@PostMapping
	public ResponseEntity<AlunoDto> save(@RequestBody AlunoForm form){
		AlunoDto dto = service.save(form);
		return ResponseEntity.ok(dto);
	}
	
}
