package com.company.pratica04.controller;

import java.util.List;

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
import com.company.pratica04.dto.aluno.AlunoIdForm;
import com.company.pratica04.dto.mentor.MentorPatchForm;
import com.company.pratica04.dto.mentor.ItemListaMentorDto;
import com.company.pratica04.dto.mentor.MentorDto;
import com.company.pratica04.dto.mentor.MentorPostForm;
import com.company.pratica04.service.MentorService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/mentores")
public class MentorController {

	@Autowired
	private MentorService service;
	
	@ApiOperation(value = "Cadastra um novo mentor.")
	@PostMapping
	public ResponseEntity<MentorDto> cadastra(@Valid @RequestBody MentorPostForm form) {
		
		MentorDto dto = service.cadastra(form);
		
		return new ResponseEntity<MentorDto>(dto, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Lista mentores cadastrados.")
	@GetMapping
	public ResponseEntity<Page<ItemListaMentorDto>> buscaTodos(Pageable pageable) {
		return ResponseEntity.ok(service.buscaTodos(pageable));
	}
	
	@ApiOperation(value = "Inicia mentoriauma mentoria.")
	@PostMapping("/{id}/mentorados")
	public ResponseEntity<MentorDto> mentoraAluno(@Parameter(description = "Id do mentor") @PathVariable(name = "id") Long idMentor, 
													@Valid @RequestBody AlunoIdForm form) {

		MentorDto dto = service.mentoraAluno(idMentor, form.getIdAluno());
		return new ResponseEntity<MentorDto>(dto, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Lista mentorados de um mentor.")
	@GetMapping("/{id}/mentorados")
	public ResponseEntity<List<AlunoDto>> listaMentorados(@Parameter(description = "Id do mentor")
											@PathVariable(name = "id") Long id) {
		List<AlunoDto> dto = service.buscaMentorados(id);
		return ResponseEntity.ok(dto);
	}
	
	@ApiOperation(value = "Encerra uma mentoria.")
	@DeleteMapping("/{id}/mentorados/{idAluno}")
	public ResponseEntity<MentorDto> encerraMentoria(@Parameter(description = "Id do mentor") @PathVariable(name = "id") Long idMentor, 
														@Parameter(description = "Id do mentorado")	@PathVariable Long idAluno) {
		service.encerraMentoria(idMentor, idAluno);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Busca um mentor pelo Id.")
	@GetMapping("/{id}")
	public ResponseEntity<MentorDto> buscaPorId(@Parameter(description = "Id do mentor") @PathVariable Long id){
		MentorDto dto = service.buscaPorId(id);
		return ResponseEntity.ok(dto);
	}
	
	@ApiOperation(value = "Atualiza um mentor.")
	@PatchMapping("/{id}")
	public ResponseEntity<MentorDto> atualiza(@Parameter(description = "Id do mentor") @PathVariable Long id, 
										@Valid @RequestBody MentorPatchForm form){
		MentorDto dto = service.atualiza(id, form);
		return ResponseEntity.ok(dto);
	}
	
	@ApiOperation(value = "Deleta um mentor.")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletaPorId(@Parameter(description = "Id do mentor") @PathVariable Long id) {
		service.deleta(id);
		return ResponseEntity.noContent().build();
	}
}
