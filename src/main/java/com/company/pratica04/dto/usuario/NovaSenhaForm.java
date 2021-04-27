package com.company.pratica04.dto.usuario;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class NovaSenhaForm {
	@NotBlank
	private String senhaAtual;
	@NotBlank
	private String novaSenha;
}
