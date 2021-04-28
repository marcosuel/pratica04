package com.company.pratica04.controller;

import javax.servlet.http.HttpServletRequest;
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

import com.company.pratica04.dto.usuario.NovaSenhaForm;
import com.company.pratica04.dto.usuario.UsuarioDto;
import com.company.pratica04.dto.usuario.UsuarioPostForm;
import com.company.pratica04.exception.ExceptionResponse;
import com.company.pratica04.service.impl.UsuarioServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl service;
	
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Cadastra um novo usuário.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@PostMapping
	public ResponseEntity<UsuarioDto> cadastra(@Valid @RequestBody UsuarioPostForm form){
		var dto = service.cadastra(form);
		return new ResponseEntity<UsuarioDto>(dto, HttpStatus.CREATED);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retorna os dados do usuário logado.")
    @ApiResponses(value = { 
            @ApiResponse(code = 401, message = "Unauthorized", response = ExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@GetMapping("/perfil")
	public ResponseEntity<UsuarioDto> buscaUsuarioLogado(HttpServletRequest request){
		var dto = service.buscaUsuarioLogado(request);
		return ResponseEntity.ok(dto);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deleta um usuário.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 403, message = "Forbidden", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleta(@PathVariable Long id){
		service.deleta(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Atualiza senha do usuário logado.")
    @ApiResponses(value = { 
    		@ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
    		@ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@PatchMapping("/perfil")
	public ResponseEntity<?> atualizaSenha(@Valid @RequestBody NovaSenhaForm form, HttpServletRequest request){
		service.atualizaSenha(form, request);
		return ResponseEntity.noContent().build();
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retorna lista do usuários cadastrados.")
    @ApiResponses(value = { 
            @ApiResponse(code = 403, message = "Forbidden", response = ExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
	@GetMapping
	public ResponseEntity<Page<UsuarioDto>> buscaTodos(Pageable pageable){
		var page = service.buscaTodos(pageable);
		return ResponseEntity.ok(page);
	}
}
