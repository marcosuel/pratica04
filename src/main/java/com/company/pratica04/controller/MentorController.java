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

import com.company.pratica04.dto.mentor.MentorDto;
import com.company.pratica04.dto.mentor.MentorForm;
import com.company.pratica04.service.MentorService;

@RestController
@RequestMapping("/mentores")
public class MentorController {

	@Autowired
	private MentorService service;
	
	
	@PostMapping
	public ResponseEntity<MentorDto> save(@Valid @RequestBody MentorForm form) {
		
		MentorDto dto = service.save(form);
		
		return new ResponseEntity<MentorDto>(dto, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(Pageable pageable) {
		return ResponseEntity.ok(service.findAll(pageable));
	}
}
