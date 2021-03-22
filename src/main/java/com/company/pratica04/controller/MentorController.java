package com.company.pratica04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.pratica04.dto.MentorDto;
import com.company.pratica04.form.MentorForm;
import com.company.pratica04.service.MentorService;

@RestController
@RequestMapping("/mentor")
public class MentorController {

	@Autowired
	private MentorService service;
	
	
	@PostMapping
	public ResponseEntity<MentorDto> save(@RequestBody MentorForm form) {
		
		MentorDto dto = service.save(form);
		
		return new ResponseEntity<MentorDto>(dto, HttpStatus.CREATED);
	}
}
