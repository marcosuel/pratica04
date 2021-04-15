package com.company.pratica03.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.mentor.MentorDto;
import com.company.pratica04.dto.mentor.MentorPostForm;
import com.company.pratica04.mapper.MentorMapper;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Mentor;
import com.company.pratica04.repository.AlunoRepository;
import com.company.pratica04.repository.MentorRepository;
import com.company.pratica04.service.MentorService;

@ExtendWith(MockitoExtension.class)
public class MentorServiceTests {

	@Mock
	private MentorRepository mentorRep;
	@Mock
	private AlunoRepository alunoRep;
	@Mock
	private MentorMapper mapper;
	
	@InjectMocks
	private MentorService service;
	
	//---------------------
	Long idMentor;
	String nomeMentor;
	String sobrenomeMentor;
	Long matriculaMentor;
	List<Aluno> mentorados;
	List<AlunoDto> mentoradosDto;
	
	MentorPostForm postForm;
	Mentor mentorNaoSalvo;
	Mentor mentorSalvo;
	MentorDto mentorDto;
	//---------------------
	
	private void initMentor() {
		idMentor = 1L;
		nomeMentor = "Bruno";
		sobrenomeMentor = "Damasceno";
		matriculaMentor = 548165L;
		mentorados = new ArrayList<Aluno>();
		mentoradosDto = new ArrayList<AlunoDto>();
		
		postForm = new MentorPostForm(nomeMentor, sobrenomeMentor, matriculaMentor);
		mentorNaoSalvo = new Mentor(null, postForm.getNome(), postForm.getSobrenome(), postForm.getMatricula(), mentorados);
		mentorSalvo = new Mentor(idMentor, mentorNaoSalvo.getNome(), mentorNaoSalvo.getSobrenome(), mentorNaoSalvo.getMatricula(), mentorNaoSalvo.getMentorados());
		mentorDto = new MentorDto(mentorSalvo.getId(), mentorSalvo.getNome(), mentorSalvo.getSobrenome(), mentorSalvo.getMatricula(), mentoradosDto);
	}
	
	
	@Test
	void givenPostFormWhenSaveThenSuccess() {
		initMentor();
		
		when(mentorRep.findByMatricula(matriculaMentor)).thenReturn(Optional.empty());
		when(mapper.toMentor(postForm)).thenReturn(mentorNaoSalvo);
		when(mentorRep.save(mentorNaoSalvo)).thenReturn(mentorSalvo);
		when(mapper.toDto(mentorSalvo)).thenReturn(mentorDto);
		
		var expected = new MentorDto(idMentor, nomeMentor, sobrenomeMentor, matriculaMentor, mentoradosDto);
		var result = service.cadastra(postForm);
		
		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getNome(), result.getNome());
		assertEquals(expected.getSobrenome(), result.getSobrenome());
		assertEquals(expected.getMatricula(), result.getMatricula());
	}
	
	
	
}
