package com.company.pratica04.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.company.pratica04.dto.mentor.ItemListaMentorDto;
import com.company.pratica04.dto.mentor.MentorDto;
import com.company.pratica04.dto.mentor.MentorPostForm;
import com.company.pratica04.model.Mentor;

@Mapper(componentModel = "spring")
public abstract class MentorMapper {

	public static final MentorMapper INSTANCE  = Mappers.getMapper(MentorMapper.class);
	
	public abstract Mentor toMentor(MentorPostForm mentorPostForm);
	
	public abstract MentorDto toDto(Mentor mentor);
	
	public abstract ItemListaMentorDto toItemListaDto(Mentor mentor);
	
}
