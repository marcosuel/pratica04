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
import com.company.pratica04.dto.turma.TurmaItemListaDto;
import com.company.pratica04.dto.turma.TurmaPatchForm;
import com.company.pratica04.dto.turma.TurmaPostForm;
import com.company.pratica04.exception.DomainException;
import com.company.pratica04.mapper.TurmaMapper;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Mentor;
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
	
	@Test
	void givenIdWhenFindByIdWithNonexistentIdThenFail() {
		var id = 999L;
		when(turmaRep.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, ()->{
			turmaService.buscaPorId(id);
		});
		verify(turmaRep, never()).save(Mockito.any(Turma.class));
	}
	
	@Test
	void givenIdWhenDeleteThenSuccess() {
		initTurma();
		var aluno = new Aluno(1L, "Zé", "Carneiro", 549L, null, new Mentor());
		turmaSalva.getAlunos().add(aluno);
		when(turmaRep.findById(idTurma)).thenReturn(Optional.of(turmaSalva));
		
		turmaService.deletaPorId(idTurma);
		
		assertEquals(null, aluno.getMentor());
		verify(turmaRep).delete(Mockito.any(Turma.class));
	}
	
	@Test
	void givenIdWhenDeleteWithNonexistentIdThenFail() {
		var id = 999L;
		when(turmaRep.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, () -> {
			turmaService.deletaPorId(id);
		});
		verify(turmaRep, never()).delete(Mockito.any(Turma.class));
	}
	
	@Test
	void givenIdAndPatchFormWhenUpdateThenSuccess() {
		initTurma();
		var nomeAtualizado = "PAA";
		var anoAtualizado = Year.of(2016);
		var patchForm = new TurmaPatchForm(nomeAtualizado, anoAtualizado);
		var turmaAtualizadaDto = new TurmaItemListaDto(idTurma, patchForm.getNome(), qtdAlunos, patchForm.getAnoLetivo());
		
		when(turmaRep.findById(idTurma)).thenReturn(Optional.of(turmaSalva));
		when(turmaRep.save(Mockito.any(Turma.class))).thenReturn(turmaSalva);
		when(mapper.toItemListaDto(Mockito.any(Turma.class))).thenReturn(turmaAtualizadaDto);
		
		var expected = new TurmaItemListaDto(idTurma, nomeAtualizado, qtdAlunos, anoAtualizado);
		var result = turmaService.atualiza(idTurma, patchForm);
		
		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getNome(), result.getNome());
		assertEquals(expected.getQuantidadeAlunos(), result.getQuantidadeAlunos());
		assertEquals(expected.getAnoLetivo(), result.getAnoLetivo());
	}
	
	@Test
	void givenIdAndPatchFormWhenUpdateWithNonexistentIdThenFail() {
		var id = 999L;
		when(turmaRep.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, () -> {
			turmaService.atualiza(id, new TurmaPatchForm());
		});
		verify(turmaRep, never()).save(Mockito.any(Turma.class));
	}
	
	@Test
	void givenIdAnPatchFormWhenUpdateWithDuplicatedAnoLetivoThenFail() {
		initTurma();
		var nomeAtualizado = "PAA";
		var anoAtualizado = Year.of(2016);
		var patchForm = new TurmaPatchForm(nomeAtualizado, anoAtualizado);
		
		var outraTurma = new Turma(2L, "FBD", 0, anoAtualizado);
		when(turmaRep.findById(idTurma)).thenReturn(Optional.of(turmaSalva));
		when(turmaRep.findByAnoLetivo(patchForm.getAnoLetivo())).thenReturn(Optional.of(outraTurma));
		
		assertThrows(DomainException.class, () -> {
			turmaService.atualiza(idTurma, patchForm);
		});
		verify(turmaRep, never()).save(Mockito.any(Turma.class));
	}
	
	@Test
	void givenIdTurmaAndIdAlunoWhenAddAlunoThenSuccesss() {
		initTurma();
		var idAluno = 2L;
		var aluno = new Aluno(idAluno, "Pedro", "Pedroso", 9846L, null, null);
		when(turmaRep.findById(idTurma)).thenReturn(Optional.of(turmaSalva));
		when(alunoService.garanteQueAlunoExiste(idAluno)).thenReturn(aluno);
		
		turmaService.adicionaAluno(idTurma, idAluno);
		
		assertEquals(idAluno, turmaSalva.getAlunos().get(0).getId());
		assertEquals(qtdAlunos+1, turmaSalva.getQuantidadeAlunos());
		verify(turmaRep).save(Mockito.any(Turma.class));
	}
	
	@Test
	void givenIdTurmaAndIdAlunoWhenAddAlunoWithNonexistentIdTurmaThenFail() {
		idTurma = 999L;
		var idAluno = 2L;
		when(turmaRep.findById(idTurma)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, () ->{
			turmaService.adicionaAluno(idTurma, idAluno);
		});
		verify(turmaRep, never()).save(Mockito.any(Turma.class));
	}
	
	@Test
	void givenIdTurmaAndIdAlunoWhenAddAlunoWithNonexistentIdAlunoThenFail() {
		initTurma();
		var idAluno = 2L;
		when(turmaRep.findById(idTurma)).thenReturn(Optional.of(turmaSalva));
		when(alunoService.garanteQueAlunoExiste(idAluno)).thenThrow(DomainException.class);
		
		assertThrows(DomainException.class, () -> {
			turmaService.adicionaAluno(idTurma, idAluno);
		});
		verify(turmaRep, never()).save(Mockito.any(Turma.class));
	}
	
	@Test
	void givenIdTurmaAndIdAlunoWhenAddAlunoWithAlunoHavingATurmaThenFail() {
		initTurma();
		var idAluno = 2L;
		var aluno = new Aluno(idAluno, "Pedro", "Pedroso", 9846L, turmaSalva, null);
		when(turmaRep.findById(idTurma)).thenReturn(Optional.of(turmaSalva));
		when(alunoService.garanteQueAlunoExiste(idAluno)).thenReturn(aluno);
		
		assertThrows(DomainException.class, () -> {
			turmaService.adicionaAluno(idTurma, idAluno);
		});
		verify(turmaRep, never()).save(Mockito.any(Turma.class));
	}
	
	
	
	private void compareExpectedTurmaDtoWithActual(TurmaDto expected, TurmaDto result) {
		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getNome(), result.getNome());
		assertEquals(expected.getQuantidadeAlunos(), result.getQuantidadeAlunos());
		assertEquals(expected.getAnoLetivo(), result.getAnoLetivo());
		assertEquals(expected.getAlunos().size(), result.getAlunos().size());
	}
}
