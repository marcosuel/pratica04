package com.company.pratica04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.pratica04.dto.TurmaDto;
import com.company.pratica04.form.TurmaForm;
import com.company.pratica04.service.TurmaService;

@RestController
@RequestMapping("/turma")
public class TurmaController {

	@Autowired
	private TurmaService service;
	
	@PostMapping
	public ResponseEntity<TurmaDto> save(@RequestBody TurmaForm form){
		TurmaDto dto = service.save(form);
		return new ResponseEntity<TurmaDto>(dto, HttpStatus.CREATED);
	}
	
}
