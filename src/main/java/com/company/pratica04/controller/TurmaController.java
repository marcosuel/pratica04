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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.pratica04.dto.aluno.AlunoIdForm;
import com.company.pratica04.dto.aluno.AlunoItemListaTurmaDto;
import com.company.pratica04.dto.turma.TurmaDto;
import com.company.pratica04.dto.turma.TurmaPostForm;
import com.company.pratica04.exception.ExceptionResponse;
import com.company.pratica04.service.impl.TurmaServiceImpl;
import com.company.pratica04.dto.turma.TurmaItemListaDto;
import com.company.pratica04.dto.turma.TurmaPatchForm;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

	@Autowired
	private TurmaServiceImpl service;
	
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Cadastra uma turma.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@PostMapping
	public ResponseEntity<TurmaDto> cadastra(@Valid @RequestBody TurmaPostForm form){
		TurmaDto dto = service.cadastra(form);
		return new ResponseEntity<TurmaDto>(dto, HttpStatus.CREATED);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Atualiza uma turma.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@PatchMapping("/{id}")
	public ResponseEntity<TurmaItemListaDto> atualiza(@PathVariable Long id, 
										@Valid @RequestBody TurmaPatchForm form){
		TurmaItemListaDto dto = service.atualiza(id, form);
		return ResponseEntity.ok(dto);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Lista turmas cadastradas.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@GetMapping
	public ResponseEntity<Page<TurmaItemListaDto>> buscaTodos(Pageable pageable){
		return ResponseEntity.ok(service.buscaTodos(pageable));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Busca uma turma pelo Id.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@GetMapping("/{id}")
	public ResponseEntity<TurmaDto> buscaPorId(@PathVariable Long id){
		return ResponseEntity.ok(service.buscaPorId(id));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deleta uma turma.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@DeleteMapping("/{id}")
	public ResponseEntity<TurmaDto> deleta(@PathVariable Long id){
		service.deletaPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Remove aluno de uma turma.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@DeleteMapping("/{id}/alunos/{idAluno}")
	public ResponseEntity<TurmaDto> removeAluno(@PathVariable(name = "id") Long idTurma, 
												@PathVariable Long idAluno){
		service.removeAluno(idTurma, idAluno);
		return ResponseEntity.noContent().build();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Adiciona aluno em uma turma.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@PostMapping("/{id}/alunos")
	public ResponseEntity<AlunoItemListaTurmaDto> adicionaAluno(@PathVariable(name = "id") Long idTurma, 
											@Valid @RequestBody AlunoIdForm form){
		AlunoItemListaTurmaDto dto = service.adicionaAluno(idTurma, form.getIdAluno());
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Lista alunos de uma turma.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@GetMapping("/{id}/alunos")
	public ResponseEntity<List<AlunoItemListaTurmaDto>> listaAlunos(@PathVariable Long id){
		List<AlunoItemListaTurmaDto> dto = service.listaAlunos(id);
		return ResponseEntity.ok(dto);
	}
}
