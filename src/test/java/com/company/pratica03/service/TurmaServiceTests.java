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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.pratica04.dto.aluno.AlunoItemListaTurmaDto;
import com.company.pratica04.dto.turma.TurmaDto;
import com.company.pratica04.dto.turma.TurmaPostForm;
import com.company.pratica04.exception.DomainException;
import com.company.pratica04.mapper.TurmaMapper;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Turma;
import com.company.pratica04.repository.TurmaRepository;
import com.company.pratica04.service.AlunoService;
import com.company.pratica04.service.TurmaService;

@ExtendWith(MockitoExtension.class)
public class TurmaServiceTests {

	@Mock
	private TurmaRepository turmaRep;
	@Mock
	private AlunoService alunoService;
	@Mock
	private TurmaMapper mapper;
	@InjectMocks
	private TurmaService turmaService;
	
	Long idTurma;
	String nomeTurma;
	int qtdAlunos;
	Year anoLetivo;
	List<Aluno> alunos;
	List<AlunoItemListaTurmaDto> alunosDto;
	
	TurmaPostForm postForm;
	Turma turmaSalva;
	Turma turmaNaoSalva;
	TurmaDto turmaDto;
	
	void initTurma() {
		idTurma = 1L;
		nomeTurma = "POO";
		qtdAlunos = 0;
		anoLetivo = Year.of(2015);
		alunos = new ArrayList<Aluno>();
		alunosDto = new ArrayList<AlunoItemListaTurmaDto>();
		postForm = new TurmaPostForm(nomeTurma, anoLetivo);
		turmaNaoSalva = new Turma(null, postForm.getNome(), qtdAlunos, postForm.getAnoLetivo(), alunos);
		turmaSalva = new Turma(idTurma, turmaNaoSalva.getNome(), turmaNaoSalva.getQuantidadeAlunos(), 
				turmaNaoSalva.getAnoLetivo(), turmaNaoSalva.getAlunos());		
		turmaDto = new TurmaDto(turmaSalva.getId(), turmaSalva.getNome(), turmaSalva.getQuantidadeAlunos(), 
				turmaSalva.getAnoLetivo(), alunosDto);
	}
	
	@Test
	void givePostFormWhenSaveThenSuccess() {
		initTurma();
		
		when(mapper.toTurma(postForm)).thenReturn(turmaNaoSalva);
		when(turmaRep.save(turmaNaoSalva)).thenReturn(turmaSalva);
		when(mapper.toDto(Mockito.any(Turma.class))).thenReturn(turmaDto);
		
		var expected = new TurmaDto(idTurma, nomeTurma, qtdAlunos, anoLetivo, alunosDto);
		var result = turmaService.cadastra(postForm);
		
		compareExpectedTurmaDtoWithActual(expected, result);
	}
	
	@Test
	void givenPostFormWhenSaveWithDuplicatedAnoLetivoThenFail() {
		initTurma();
		when(turmaRep.findByAnoLetivo(anoLetivo)).thenReturn(Optional.of(turmaSalva));
		
		assertThrows(DomainException.class, () -> {
			turmaService.cadastra(postForm);
		});
		verify(turmaRep, never()).save(Mockito.any(Turma.class));
	}
	
	@Test
	void givenIdWhenFindByIdThenSuccess() {
		initTurma();
		when(turmaRep.findById(idTurma)).thenReturn(Optional.of(turmaSalva));
		when(mapper.toDto(turmaSalva)).thenReturn(turmaDto);
		
		var expected = new TurmaDto(idTurma, nomeTurma, qtdAlunos, anoLetivo, alunosDto);
		var result = turmaService.buscaPorId(idTurma);
		
		compareExpectedTurmaDtoWithActual(expected, result);
	}
	
	private void compareExpectedTurmaDtoWithActual(TurmaDto expected, TurmaDto result) {
		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getNome(), result.getNome());
		assertEquals(expected.getQuantidadeAlunos(), result.getQuantidadeAlunos());
		assertEquals(expected.getAnoLetivo(), result.getAnoLetivo());
		assertEquals(expected.getAlunos().size(), result.getAlunos().size());
	}
}