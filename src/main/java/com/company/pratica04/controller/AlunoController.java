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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.aluno.AlunoPostForm;
import com.company.pratica04.exception.ExceptionResponse;
import com.company.pratica04.service.impl.AlunoServiceImpl;
import com.company.pratica04.dto.aluno.AlunoPatchForm;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
	
	@Autowired
	private AlunoServiceImpl service;
	
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Cadastra um novo aluno.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
    		@ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@PostMapping
	public ResponseEntity<AlunoDto> cadastra(@Valid @RequestBody AlunoPostForm form){
		AlunoDto dto = service.cadastra(form);
		return new ResponseEntity<AlunoDto>(dto, HttpStatus.CREATED);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Atualiza um aluno.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
    		@ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@PatchMapping("/{id}")
	public ResponseEntity<AlunoDto> atualiza(@PathVariable Long id, @Valid @RequestBody AlunoPatchForm form){
		AlunoDto dto = service.atualiza(id, form);
		return ResponseEntity.ok(dto);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Lista alunos cadastrados.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 403, message = "Forbidden", response = ExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@GetMapping
	public ResponseEntity<Page<AlunoDto>> buscaTodos(Pageable pageable){
		return ResponseEntity.ok(service.buscaTodos(pageable));
	}
	
	@ApiOperation(value = "Busca um aluno pelo Id.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
    		@ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@GetMapping("/{id}")
	public ResponseEntity<AlunoDto> buscaPorId(@PathVariable Long id){
		return ResponseEntity.ok(service.buscaPorId(id));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deleta um aluno pelo Id.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
    		@ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleta(@PathVariable Long id){
		service.deleta(id);
		return ResponseEntity.noContent().build();
	}
}
