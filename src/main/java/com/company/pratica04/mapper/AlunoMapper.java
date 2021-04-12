package com.company.pratica04.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.company.pratica04.dto.aluno.AlunoPostForm;
import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.aluno.AlunoItemListaTurmaDto;
import com.company.pratica04.model.Aluno;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class AlunoMapper {

	public static final AlunoMapper INSTANCE = Mappers.getMapper(AlunoMapper.class);
	
	public abstract Aluno toAluno(AlunoPostForm alunoPostForm);
	
	public abstract AlunoDto toDto(Aluno aluno);
	
	public abstract AlunoItemListaTurmaDto toItemTurmaDto(Aluno aluno);
}
