package com.company.pratica04.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.company.pratica04.dto.aluno.AlunoPostForm;
import com.company.pratica04.dto.aluno.AlunoPutForm;
import com.company.pratica04.model.Aluno;

@Mapper(componentModel = "spring")
public abstract class AlunoMapper {

	public static final AlunoMapper INSTANCE = Mappers.getMapper(AlunoMapper.class);
	
	public abstract Aluno toAluno(AlunoPostForm alunoPostForm);
	
	public abstract Aluno toAluno(AlunoPutForm alunoPutForm);
}
