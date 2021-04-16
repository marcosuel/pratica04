package com.company.pratica03.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Year;
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
import com.company.pratica04.dto.mentor.MentorPatchForm;
import com.company.pratica04.dto.mentor.MentorPostForm;
import com.company.pratica04.dto.turma.TurmaItemAlunoDto;
import com.company.pratica04.exception.DomainException;
import com.company.pratica04.mapper.MentorMapper;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Mentor;
import com.company.pratica04.model.Turma;
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
	Long idMentor, idAluno;
	String nomeMentor;
	String sobrenomeMentor;
	Long matriculaMentor;
	List<Aluno> mentorados;
	List<AlunoDto> mentoradosDto;
	
	String nomeAtualizado;
	String sobrenomeAtualizado;
	Long matriculaAtualizada;
	
	MentorPatchForm patchForm;
	MentorPostForm postForm;
	Mentor mentorNaoSalvo;
	Mentor mentorSalvo;
	MentorDto mentorDto;
	Aluno aluno;
	Turma turma;
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
	
	private void initAlunoAndTurma() {
		idAluno = 2L;
		turma = new Turma(1L, "Turma de FBD", 0, Year.of(2015));
		aluno = new Aluno(idAluno, "Joana", "Pinheiro", 98732L, turma, null);
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
		
		compareExpectedMentorDtoWithActual(expected, result);
	}
	
	@Test
	void givenPostFormWhenSaveWithDuplicatedMatriculaThenFail() {
		initMentor();

		when(mentorRep.findByMatricula(matriculaMentor)).thenReturn(Optional.of(mentorSalvo));
		
		assertThrows(DomainException.class, ()->{
			service.cadastra(postForm);
		});
		verify(mentorRep, never()).save(mentorNaoSalvo);
	}
	
	@Test
	void givenIdWhenFindByIdThenSuccess() {
		initMentor();
		
		when(mentorRep.findById(idMentor)).thenReturn(Optional.of(mentorSalvo));
		when(mapper.toDto(mentorSalvo)).thenReturn(mentorDto);
		
		var expected = new MentorDto(idMentor, nomeMentor, sobrenomeMentor, matriculaMentor, mentoradosDto);
		var result = service.buscaPorId(idMentor);
		
		compareExpectedMentorDtoWithActual(expected, result);
	}
	
	@Test
	void givenIdWhenFindByIdWithNonexistentIdThenFail() {
		var id = 999L;
		when(mentorRep.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, ()->{
			service.buscaPorId(id);
		});
	}
	
	@Test
	void givenIdWhenDeleteThenSuccess() {
		when(mentorRep.existsById(idMentor)).thenReturn(true);
		
		service.deleta(idMentor);
		verify(mentorRep).deleteById(idMentor);
	}
	
	@Test
	void givenIdWhenDeleteWithNonexistentIdThenFail() {
		var id = 999L;
		when(mentorRep.existsById(id)).thenReturn(false);
		
		assertThrows(DomainException.class, () -> {
			service.deleta(id);
		});
	}
	
	@Test
	void givenIdAndPatchFormWhenUpdateThenSuccess() {
		initMentor();
		nomeAtualizado = "Pedro";
		sobrenomeAtualizado = "Vieira";
		matriculaAtualizada = 542879L;
		
		patchForm = new MentorPatchForm(nomeAtualizado, sobrenomeAtualizado, matriculaAtualizada);
		var mentorDtoAtualizado = new MentorDto(idMentor, patchForm.getNome(), patchForm.getSobrenome(), 
				patchForm.getMatricula(), mentoradosDto);
		
		when(mentorRep.findById(idMentor)).thenReturn(Optional.of(mentorSalvo));
		when(mentorRep.findByMatricula(matriculaAtualizada)).thenReturn(Optional.of(mentorSalvo));
		when(mentorRep.save(mentorSalvo)).thenReturn(mentorSalvo);
		when(mapper.toDto(mentorSalvo)).thenReturn(mentorDtoAtualizado);
		
		var expected = new MentorDto(idMentor, nomeAtualizado, sobrenomeAtualizado, matriculaAtualizada, mentoradosDto);
		var result = service.atualiza(idMentor, patchForm);
		
		compareExpectedMentorDtoWithActual(expected, result);
	}
	
	@Test
	void givenIdAndPatchFormWhenUpdateWithNonexistentIdThenFail() {
		var id = 999L;
		patchForm = new MentorPatchForm();
		
		when(mentorRep.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, () -> {
			service.atualiza(id, patchForm);
		});
		verify(mentorRep, never()).save(mentorSalvo);
	}
	
	@Test
	void givenIdAndPatchWhenUpdateWithDuplicatedMatriculaThenFail() {
		initMentor();
		patchForm = new MentorPatchForm(nomeAtualizado, sobrenomeAtualizado, matriculaAtualizada);
		var outroMentor = new Mentor(2L, patchForm.getNome(), patchForm.getSobrenome(), patchForm.getMatricula(), mentorados);
		
		when(mentorRep.findById(idMentor)).thenReturn(Optional.of(mentorSalvo));
		when(mentorRep.findByMatricula(patchForm.getMatricula())).thenReturn(Optional.of(outroMentor));
		
		assertThrows(DomainException.class, () -> {
			service.atualiza(idMentor, patchForm);
		});
	}
	
	@Test
	void givenIdMentorAndIdAlunoWhenMentoraAlunoThenSuccess() {
		initMentor();
		initAlunoAndTurma();
		var turmaDto = new TurmaItemAlunoDto(turma.getId(), turma.getNome(), turma.getAnoLetivo());
		var alunoDto = new AlunoDto(aluno.getId(), aluno.getNome(), aluno.getSobrenome(), aluno.getMatricula(), turmaDto);
		turma.getAlunos().add(aluno);
		mentorDto.getMentorados().add(alunoDto);
		
		when(mentorRep.findById(idMentor)).thenReturn(Optional.of(mentorSalvo));
		when(alunoRep.findById(idAluno)).thenReturn(Optional.of(aluno));
		when(mentorRep.save(mentorSalvo)).thenReturn(mentorSalvo);
		when(mapper.toDto(mentorSalvo)).thenReturn(mentorDto);
		
		var expected = new MentorDto(idMentor, nomeMentor, sobrenomeMentor, matriculaMentor, mentoradosDto);
		var result = service.mentoraAluno(idMentor, idAluno);
		
		compareExpectedMentorDtoWithActual(expected, result);
	}
	
	@Test
	void givenIdMentorAndIdAlunoWhenMentoraAlunoWithNonexistentIdMentorThenFail() {
		idAluno = 2L;
		idMentor = 1L;
		
		when(mentorRep.findById(idMentor)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, () -> {
			service.mentoraAluno(idMentor, idAluno);
		});
		verify(mentorRep, never()).save(mentorSalvo);
	}
	
	@Test
	void givenIdMentorAndIdAlunoWhenMentoraAlunoWithNonexistentIdAlunoThenFail() {
		initMentor();
		idAluno = 2L;
		
		when(mentorRep.findById(idMentor)).thenReturn(Optional.of(mentorSalvo));
		
		assertThrows(DomainException.class, () -> {
			service.mentoraAluno(idMentor, idAluno);
		});
		verify(mentorRep, never()).save(mentorSalvo);
	}
	
	@Test
	void givenIdMentorAndIdAlunoWhenMentoraAlunoWithAlunoWithoutTurmaThenFail() {
		initMentor();
		initAlunoAndTurma();
		aluno.setTurma(null);
		
		when(mentorRep.findById(idMentor)).thenReturn(Optional.of(mentorSalvo));
		when(alunoRep.findById(idAluno)).thenReturn(Optional.of(aluno));
		
		assertThrows(DomainException.class, () -> {
			service.mentoraAluno(idMentor, idAluno);			
		});
		verify(mentorRep, never()).save(mentorSalvo);
	}
	
	@Test
	void givenIdMentorAndIdAlunoWhenMentoraAlunoWithAlunoHavingAMentorThenFail() {
		initMentor();
		initAlunoAndTurma();
		aluno.setMentor(mentorSalvo);
		
		when(mentorRep.findById(idMentor)).thenReturn(Optional.of(mentorSalvo));
		when(alunoRep.findById(idAluno)).thenReturn(Optional.of(aluno));
		
		assertThrows(DomainException.class, () -> {
			service.mentoraAluno(idMentor, idAluno);
		});
		verify(mentorRep, never()).save(mentorSalvo);
	}
	
	@Test
	void givenIdMentorAndIdAlunoWhenMentoraAlunoWithMentorHaving3StudentsInSameClassThenFail() {
		initMentor();
		initAlunoAndTurma();
		
		when(mentorRep.findById(idMentor)).thenReturn(Optional.of(mentorSalvo));
		when(alunoRep.findById(idAluno)).thenReturn(Optional.of(aluno));
		when(mentorRep.countByIdAndMentoradosTurmaId(idMentor, turma.getId())).thenReturn(3L);
		
		assertThrows(DomainException.class, () -> {
			service.mentoraAluno(idMentor, idAluno);
		});
		verify(mentorRep, never()).save(mentorSalvo);
	}
	
	@Test
	void givenIdMentorAndIdAlunoWhenEncerraMentoriaThenSuccess() {
		initMentor();
		initAlunoAndTurma();
		aluno.setMentor(mentorSalvo);
		mentorSalvo.getMentorados().add(aluno);
		
		when(mentorRep.findById(idMentor)).thenReturn(Optional.of(mentorSalvo));
		
		service.encerraMentoria(idMentor, idAluno);
		verify(mentorRep).save(mentorSalvo);
	}
	
	@Test
	void givenIdMentorAndIdAlunoWhenEncerraMentoriaWithNonexistentIdMentorThenFail() {
		idMentor = 999L;
		idAluno = 2L;
		when(mentorRep.findById(idMentor)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, () -> {
			service.encerraMentoria(idMentor, idAluno);
		});
		verify(mentorRep, never()).save(mentorSalvo);
	}
	
	@Test
	void givenIdMentorAndIdAlunoWhenEncerraMentoriaWithAlunoMentorDifferentFromIdMentorThenFail() {
		initMentor();
		initAlunoAndTurma();
		var outroMentor = new Mentor(2L, "Francisco", "Cunha", 87165L, new ArrayList<Aluno>());
		outroMentor.getMentorados().add(aluno);
		aluno.setMentor(outroMentor);
		
		when(mentorRep.findById(idMentor)).thenReturn(Optional.of(mentorSalvo));
		assertThrows(DomainException.class, () -> {
			service.encerraMentoria(idMentor, idAluno);
		});
		verify(mentorRep, never()).save(mentorSalvo);
	}
	
	
	
	void compareExpectedMentorDtoWithActual(MentorDto expected, MentorDto result) {
		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getNome(), result.getNome());
		assertEquals(expected.getSobrenome(), result.getSobrenome());
		assertEquals(expected.getMatricula(), result.getMatricula());
		assertEquals(expected.getMentorados().size(), result.getMentorados().size());		
	}
	
}
