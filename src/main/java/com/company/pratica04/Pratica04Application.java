package com.company.pratica04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.company.pratica04.dto.aluno.AlunoDto;

@SpringBootApplication
public class Pratica04Application {

	public static void main(String[] args) {
		AlunoDto dto = new AlunoDto();
		
		SpringApplication.run(Pratica04Application.class, args);
		
		
	}

}
