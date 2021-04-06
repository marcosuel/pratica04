package com.company.pratica04.dto.mentor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ItemListaMentorDto {
	private Long id;
	private String nome;
	private String sobrenome;
	private Long matricula;
}
