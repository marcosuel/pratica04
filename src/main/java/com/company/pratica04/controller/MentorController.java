package com.company.pratica04.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.company.pratica04.dto.aluno.AlunoIdForm;
import com.company.pratica04.dto.mentor.MentorDto;
import com.company.pratica04.dto.mentor.MentorForm;
import com.company.pratica04.service.MentorService;

@RestController
@RequestMapping("/mentores")
public class MentorController {

	@Autowired
	private MentorService service;
	
	
	@PostMapping
	public ResponseEntity<MentorDto> cadastra(@Valid @RequestBody MentorForm form) {
		
		MentorDto dto = service.cadastra(form);
		
		return new ResponseEntity<MentorDto>(dto, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Page<?>> buscaTodos(Pageable pageable) {
		return ResponseEntity.ok(service.buscaTodos(pageable));
	}
	
	@PostMapping("/{id}/mentorados")
	public ResponseEntity<MentorDto> mentoraAluno(@PathVariable(name = "id") Long idMentor, @Valid @RequestBody AlunoIdForm form) {
		MentorDto dto = service.mentoraAluno(idMentor, form.getId());
		return new ResponseEntity<MentorDto>(dto, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idMentor}/mentorados/{idAluno}")
	public ResponseEntity<MentorDto> encerraMentoria(@PathVariable(name = "idMentor") Long idMentor, @PathVariable(name = "idAluno") Long idAluno) {
		service.encerraMentoria(idMentor, idAluno);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscaPorId(@PathVariable Long id){
		MentorDto dto = service.buscaPorId(id);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletaPorId(@PathVariable Long id) {
		service.deleta(id);
		return ResponseEntity.noContent().build();
	}
}
