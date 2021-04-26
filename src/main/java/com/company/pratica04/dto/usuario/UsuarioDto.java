package com.company.pratica04.dto.usuario;

import lombok.Setter;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor
public class UsuarioDto {
	private Long id;
	private String nome;
	private String email;
	private List<PerfilDto> perfis;
}
