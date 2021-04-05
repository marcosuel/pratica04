package com.company.pratica04.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.company.pratica04.dto.turma.TurmaDto;
import com.company.pratica04.dto.turma.TurmaItemListaDto;
import com.company.pratica04.dto.turma.TurmaPostForm;
import com.company.pratica04.model.Turma;

@Mapper(componentModel = "spring")
public abstract class TurmaMapper {

	public static final TurmaMapper INSTANCE = Mappers.getMapper(TurmaMapper.class);
	
	public abstract Turma toTurma(TurmaPostForm turmaPostForm);
	
	public abstract TurmaDto toDto(Turma turma);
	
	public abstract TurmaItemListaDto toItemListaDto(Turma turma);
	
}
