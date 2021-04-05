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

import com.company.pratica04.dto.aluno.AlunoIdForm;
import com.company.pratica04.dto.aluno.AlunoItemListaTurmaDto;
import com.company.pratica04.dto.turma.TurmaDto;
import com.company.pratica04.dto.turma.TurmaPostForm;
import com.company.pratica04.dto.turma.TurmaItemListaDto;
import com.company.pratica04.dto.turma.TurmaPatchForm;
import com.company.pratica04.service.TurmaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

	@Autowired
	private TurmaService service;
	
	@ApiOperation(value = "Cadastra uma turma.")
	@PostMapping
	public ResponseEntity<TurmaDto> cadastra(@Valid @RequestBody TurmaPostForm form){
		TurmaDto dto = service.cadastra(form);
		return new ResponseEntity<TurmaDto>(dto, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Atualiza uma turma.")
	@PatchMapping("/{id}")
	public ResponseEntity<TurmaItemListaDto> atualiza(@Parameter(description = "Id da turma") @PathVariable Long id, 
										@Valid @RequestBody TurmaPatchForm form){
		TurmaItemListaDto dto = service.atualiza(id, form);
		return ResponseEntity.ok(dto);
	}
	
	@ApiOperation(value = "Lista turmas cadastradas.")
	@GetMapping
	public ResponseEntity<Page<TurmaItemListaDto>> buscaTodos(Pageable pageable){
		return ResponseEntity.ok(service.buscaTodos(pageable));
	}
	
	@ApiOperation(value = "Busca uma turma pelo Id.")
	@GetMapping("/{id}")
	public ResponseEntity<TurmaDto> buscaPorId(@Parameter(description = "Id da turma") @PathVariable Long id){
		return ResponseEntity.ok(service.buscaPorId(id));
	}
	
	@ApiOperation(value = "Deleta uma turma.")
	@DeleteMapping("/{id}")
	public ResponseEntity<TurmaDto> deleta(@Parameter(description = "Id da turma") @PathVariable Long id){
		service.deletaPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Remove aluno de uma turma.")
	@DeleteMapping("/{id}/alunos/{idAluno}")
	public ResponseEntity<TurmaDto> removeAluno(@Parameter(description = "Id da turma") @PathVariable(name = "id") Long idTurma, 
												@Parameter(description = "Id do aluno") @PathVariable Long idAluno){
		service.removeAluno(idTurma, idAluno);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Adiciona aluno em uma turma.")
	@PostMapping("/{id}/alunos")
	public ResponseEntity<AlunoItemListaTurmaDto> adicionaAluno(@Parameter(description = "Id da turma") @PathVariable(name = "id") Long idTurma, 
											@Valid @RequestBody AlunoIdForm form){
		AlunoItemListaTurmaDto dto = service.adicionaAluno(idTurma, form.getId());
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Lista alunos de uma turma.")
	@GetMapping("/{id}/alunos")
	public ResponseEntity<List<AlunoItemListaTurmaDto>> listaAlunos(@Parameter(description = "Id da turma") @PathVariable Long id){
		List<AlunoItemListaTurmaDto> dto = service.listaAlunos(id);
		return ResponseEntity.ok(dto);
	}
}
