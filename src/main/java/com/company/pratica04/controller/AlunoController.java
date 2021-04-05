package com.company.pratica04.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.aluno.AlunoPostForm;
import com.company.pratica04.dto.aluno.AlunoPutForm;
import com.company.pratica04.service.AlunoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
	
	@Autowired
	private AlunoService service;

	@ApiOperation(value = "Cadastra um novo aluno.")
	@PostMapping
	public ResponseEntity<AlunoDto> cadastra(@Valid @RequestBody AlunoPostForm form){
		AlunoDto dto = service.cadastra(form);
		System.err.println("dd");
		return new ResponseEntity<AlunoDto>(dto, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Atualiza um aluno.")
	@PatchMapping("/{id}")
	public ResponseEntity<AlunoDto> atualiza(@Parameter(description = "Id do aluno") @PathVariable Long id, @Valid @RequestBody AlunoPutForm form){
		AlunoDto dto = service.atualiza(id, form);
		return ResponseEntity.ok(dto);
	}
	
	@ApiOperation(value = "Lista alunos cadastrados.")
	@GetMapping
	public ResponseEntity<Page<AlunoDto>> buscaTodos(Pageable pageable){
		return ResponseEntity.ok(service.buscaTodos(pageable));
	}
	
	@ApiOperation(value = "Busca um aluno pelo Id.")
	@GetMapping("/{id}")
	public ResponseEntity<AlunoDto> buscaPorId(@Parameter(description = "Id do aluno") @PathVariable Long id){
		return ResponseEntity.ok(service.buscaPorId(id));
	}
	
	@ApiOperation(value = "Deleta um aluno pelo Id.")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleta(@Parameter(description = "Id do aluno") @PathVariable Long id){
		service.deleta(id);
		return ResponseEntity.noContent().build();
	}
}
