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
import com.company.pratica04.dto.aluno.AlunoItemListaTurmaDto;
import com.company.pratica04.dto.turma.TurmaDto;
import com.company.pratica04.dto.turma.TurmaForm;
import com.company.pratica04.service.TurmaService;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

	@Autowired
	private TurmaService service;
	
	@PostMapping
	public ResponseEntity<TurmaDto> cadastra(@Valid @RequestBody TurmaForm form){
		TurmaDto dto = service.cadastra(form);
		return new ResponseEntity<TurmaDto>(dto, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Page<?>> buscaTodos(Pageable pageable){
		return ResponseEntity.ok(service.buscaTodos(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TurmaDto> buscaPorId(@PathVariable Long id){
		return ResponseEntity.ok(service.buscaPorId(id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<TurmaDto> deleta(@PathVariable Long id){
		service.deletaPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{idTurma}/alunos/{idAluno}")
	public ResponseEntity<TurmaDto> removeAluno(@PathVariable Long idTurma, @PathVariable Long idAluno){
		service.removeAluno(idTurma, idAluno);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/{idTurma}/alunos")
	public ResponseEntity<?> adicionaAluno(@PathVariable Long idTurma, @Valid @RequestBody AlunoIdForm form){
		AlunoItemListaTurmaDto dto = service.adicionaAluno(idTurma, form.getId());
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
}
