package com.company.pratica04.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.pratica04.dto.usuario.UsuarioDto;
import com.company.pratica04.dto.usuario.UsuarioPostForm;
import com.company.pratica04.service.impl.UsuarioServiceImpl;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl service;
	
	@PostMapping
	public ResponseEntity<UsuarioDto> cadastra(@Valid @RequestBody UsuarioPostForm form){
		var dto = service.cadastra(form);
		return new ResponseEntity<UsuarioDto>(dto, HttpStatus.CREATED);
	}
	
	@GetMapping("/perfil")
	public ResponseEntity<UsuarioDto> buscaUsuarioLogado(HttpServletRequest request){
		var dto = service.buscaUsuarioLogado(request);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleta(@PathVariable Long id){
		service.deleta(id);
		return ResponseEntity.noContent().build();
	}
}
