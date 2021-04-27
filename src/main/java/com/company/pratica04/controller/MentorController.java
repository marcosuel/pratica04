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

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.aluno.AlunoIdForm;
import com.company.pratica04.dto.mentor.MentorPatchForm;
import com.company.pratica04.dto.mentor.ItemListaMentorDto;
import com.company.pratica04.dto.mentor.MentorDto;
import com.company.pratica04.dto.mentor.MentorPostForm;
import com.company.pratica04.exception.ExceptionResponse;
import com.company.pratica04.service.impl.MentorServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/mentores")
public class MentorController {

	@Autowired
	private MentorServiceImpl service;
	
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Cadastra um novo mentor.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@PostMapping
	public ResponseEntity<MentorDto> cadastra(@Valid @RequestBody MentorPostForm form) {
		
		MentorDto dto = service.cadastra(form);
		
		return new ResponseEntity<MentorDto>(dto, HttpStatus.CREATED);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Lista mentores cadastrados.")
    @ApiResponses(value = { 
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@GetMapping
	public ResponseEntity<Page<ItemListaMentorDto>> buscaTodos(Pageable pageable) {
		return ResponseEntity.ok(service.buscaTodos(pageable));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Inicia uma mentoria.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@PostMapping("/{id}/mentorados")
	public ResponseEntity<MentorDto> mentoraAluno(@PathVariable(name = "id") Long idMentor, 
													@Valid @RequestBody AlunoIdForm form) {

		MentorDto dto = service.mentoraAluno(idMentor, form.getIdAluno());
		return new ResponseEntity<MentorDto>(dto, HttpStatus.CREATED);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Lista mentorados de um mentor.")
    @ApiResponses(value = { 
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@GetMapping("/{id}/mentorados")
	public ResponseEntity<List<AlunoDto>> listaMentorados(@PathVariable(name = "id") Long id) {
		List<AlunoDto> dto = service.buscaMentorados(id);
		return ResponseEntity.ok(dto);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Encerra uma mentoria.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@DeleteMapping("/{id}/mentorados/{idAluno}")
	public ResponseEntity<MentorDto> encerraMentoria(@PathVariable(name = "id") Long idMentor, 
														@PathVariable Long idAluno) {
		service.encerraMentoria(idMentor, idAluno);
		return ResponseEntity.noContent().build();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Busca um mentor pelo Id.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@GetMapping("/{id}")
	public ResponseEntity<MentorDto> buscaPorId(@PathVariable Long id){
		MentorDto dto = service.buscaPorId(id);
		return ResponseEntity.ok(dto);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Atualiza um mentor.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@PatchMapping("/{id}")
	public ResponseEntity<MentorDto> atualiza(@PathVariable Long id, 
										@Valid @RequestBody MentorPatchForm form){
		MentorDto dto = service.atualiza(id, form);
		return ResponseEntity.ok(dto);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deleta um mentor.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletaPorId(@PathVariable Long id) {
		service.deleta(id);
		return ResponseEntity.noContent().build();
	}
}
