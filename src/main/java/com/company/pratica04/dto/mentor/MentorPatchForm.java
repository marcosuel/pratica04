package com.company.pratica04.dto.mentor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class MentorPatchForm {
	@NotBlank
	private String nome;
	@NotBlank
	private String sobrenome;
	@NotNull
	private Long matricula;
}
