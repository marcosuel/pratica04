package com.company.pratica03.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Year;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.aluno.AlunoPatchForm;
import com.company.pratica04.dto.aluno.AlunoPostForm;
import com.company.pratica04.dto.turma.TurmaItemAlunoDto;
import com.company.pratica04.exception.DomainException;
import com.company.pratica04.mapper.AlunoMapper;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Turma;
import com.company.pratica04.repository.AlunoRepository;
import com.company.pratica04.repository.TurmaRepository;
import com.company.pratica04.service.AlunoService;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTests {

	@Mock
	private AlunoRepository alunoRep;
	@Mock
	private TurmaRepository turmaRep;
	@Mock
	private AlunoMapper alunoMapper;
	
	@InjectMocks
	private AlunoService service;

	
	//objects
	AlunoPostForm alunoPostForm;
	AlunoPatchForm alunoPatchForm;
	Aluno alunoNaoSalvo;
	Aluno alunoSalvo;
	Aluno alunoAtualizado;
	Turma turma;
	TurmaItemAlunoDto turmaItem;
	AlunoDto alunoDto;
	//variables
	Long idAluno;
	Long idTurma;
	String nomeAluno;
	String sobrenome;
	String nomeTurma;
	Long matricula;
	Year ano;
	int qtdAlunos;
	String nomeAtualizado;
	String sobrenomeAtualizado;
	Long matriculaAtualizada;

	
	@BeforeEach
	public void setup() {
		//setting up attributes
		idAluno = 1L;
		idTurma = 2L;
		nomeAluno = "José";
		sobrenome = "Alberto";
		nomeTurma = "Turma de POO";
		matricula = 542184L;
		ano = Year.of(2017);
		qtdAlunos = 1;
		nomeAtualizado = "Carlos";
		sobrenomeAtualizado = "Silva";
		matriculaAtualizada = 542842L;

		//setting up objects
		alunoPatchForm = new AlunoPatchForm(nomeAtualizado, sobrenomeAtualizado, matriculaAtualizada);
		alunoPostForm = new AlunoPostForm(nomeAluno, sobrenome, matricula, idTurma);
		turma = new Turma(idTurma, nomeTurma, qtdAlunos, ano, new ArrayList<Aluno>());
		alunoNaoSalvo = new Aluno(alunoPostForm.getNome(), alunoPostForm.getSobrenome(), alunoPostForm.getMatricula(), turma);
		alunoSalvo = new Aluno(idAluno, alunoNaoSalvo.getNome(), alunoNaoSalvo.getSobrenome(), alunoNaoSalvo.getMatricula(), alunoNaoSalvo.getTurma(), null);
		alunoAtualizado = new Aluno(idAluno, alunoPatchForm.getNome(), alunoPatchForm.getSobrenome(), alunoPatchForm.getMatricula(), alunoNaoSalvo.getTurma(), null);
		turmaItem = new TurmaItemAlunoDto(turma.getId(), turma.getNome(), turma.getAnoLetivo());
		alunoDto = new AlunoDto(alunoSalvo.getId(), alunoSalvo.getNome(), alunoSalvo.getSobrenome(), alunoSalvo.getMatricula(), turmaItem);
	}
	
	@Test
	void givenPostFormWhenSaveThenSuccess() {
		var qtdExcepted = turma.getQuantidadeAlunos()+1;
		
		//setting up mocks
		when(alunoRep.findByMatricula(alunoPostForm.getMatricula())).thenReturn(Optional.empty());
		when(turmaRep.findById(turma.getId())).thenReturn(Optional.of(turma));
		when(alunoRep.save(alunoNaoSalvo)).thenReturn(alunoSalvo);
		when(alunoMapper.toAluno(alunoPostForm)).thenReturn(alunoNaoSalvo);
		when(alunoMapper.toDto(alunoSalvo)).thenReturn(alunoDto);
		
		//running code
		var result = service.cadastra(alunoPostForm);
		var turmaResult = result.getTurma();
		
		var expected = new AlunoDto(idAluno, nomeAluno, sobrenome, matricula, turmaItem);
		var turmaExpected = new TurmaItemAlunoDto(idTurma, nomeTurma, ano);
		
		//running asserts
		assertEquals(qtdExcepted, turma.getQuantidadeAlunos());
		compareExpectedItemTurmaDtoWithActual(turmaExpected, turmaResult);
		compareExceptedAlunoDtoWithActual(expected, result);
	}
	
	@Test
	void givenPostFormWhenSaveWithNonexistentTurmaIdThenFail() {
		var id = 999L;
		alunoPostForm.setIdTurma(id);
		when(turmaRep.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, () -> {
			service.cadastra(alunoPostForm);
		});
		verify(alunoRep, never()).save(alunoNaoSalvo);
	}
	
	@Test
	void givenPostFormWhenSaveWithDuplicatedMatriculaThenFail() {
		when(alunoRep.findByMatricula(alunoPostForm.getMatricula())).thenReturn(Optional.of(alunoSalvo));
		when(turmaRep.findById(alunoPostForm.getIdTurma())).thenReturn(Optional.of(turma));
		
		assertThrows(DomainException.class, () -> {
			service.cadastra(alunoPostForm);
		});
		verify(alunoRep, never()).save(alunoNaoSalvo);
	}
	
	@Test
	void givenIdWhenFindByIdThenSuccess() {
		var id = alunoSalvo.getId();
		when(alunoRep.findById(id)).thenReturn(Optional.of(alunoSalvo));
		
		var result = service.buscaPorId(id);
		var expected = new AlunoDto(alunoSalvo.getId(), alunoSalvo.getNome(), alunoSalvo.getSobrenome(), alunoSalvo.getMatricula(), turmaItem);
		
		compareExceptedAlunoDtoWithActual(expected, result);
	}
	
	@Test
	void givenIdWhenFindByIdWithNonexistentIdThenFail() {
		var id = 999L;
		when(alunoRep.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, () -> {
			service.buscaPorId(id);
		});
	}
	
	@Test
	void givenIdWhenDeleteThenSuccess() {
		var qtdExcepted = turma.getQuantidadeAlunos()-1;
		var id = alunoSalvo.getId();
		when(alunoRep.findById(id)).thenReturn(Optional.of(alunoSalvo));
		service.deleta(id);
		
		Mockito.verify(alunoRep).delete(alunoSalvo);
		assertEquals(qtdExcepted, turma.getQuantidadeAlunos());
	}
	
	@Test
	void givenIdWhenDeleteWithNonexistentIdThenFail() {
		var id = 999L;
		when(alunoRep.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, () -> {
			service.deleta(id);
		});
		verify(alunoRep, never()).delete(alunoSalvo);
	}
	
	@Test
	void givenPatchFormWhenUpdateThenSuccess() {
		var alunoDtoAtualizado = new AlunoDto(alunoAtualizado.getId(), alunoAtualizado.getNome(), 
				alunoAtualizado.getSobrenome(), alunoAtualizado.getMatricula(), turmaItem);
		
		when(alunoRep.findById(alunoSalvo.getId())).thenReturn(Optional.of(alunoSalvo));
		when(alunoRep.save(alunoSalvo)).thenReturn(alunoAtualizado);
		when(alunoMapper.toDto(alunoSalvo)).thenReturn(alunoDtoAtualizado);
		
		var expected = new AlunoDto(idAluno, nomeAtualizado, sobrenomeAtualizado, matriculaAtualizada, turmaItem);
		var result = service.atualiza(alunoSalvo.getId(), alunoPatchForm);
		
		compareExceptedAlunoDtoWithActual(expected, result);
	}
	
	@Test
	void givenIdAndPatchFormWhenUpdateAlunoWithNonexistentIdThenFail() {
		var id = 999L;
		when(alunoRep.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, () -> {
			service.atualiza(id, alunoPatchForm);
		});
		verify(alunoRep, never()).save(alunoSalvo);
	}

	@Test
	void givenPatchFormWhenUpdateAlunoWithDuplicatedMatriculaThenFail() {
		var outroAluno = new Aluno(22L, "Pedro", "Barbosa", matriculaAtualizada, turma, null);
		
		when(alunoRep.findById(alunoSalvo.getId())).thenReturn(Optional.of(alunoSalvo));
		when(alunoRep.findByMatricula(matriculaAtualizada)).thenReturn(Optional.of(outroAluno));
		
		assertThrows(DomainException.class, () -> {
			service.atualiza(idAluno, alunoPatchForm);
		});
		verify(alunoRep, never()).save(alunoSalvo);
	}
	
	private void compareExceptedAlunoDtoWithActual(AlunoDto expected, AlunoDto result) {
		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getNome(), result.getNome());
		assertEquals(expected.getSobrenome(), result.getSobrenome());
		assertEquals(expected.getMatricula(), result.getMatricula());		
	}
	
	private void compareExpectedItemTurmaDtoWithActual(TurmaItemAlunoDto expected, TurmaItemAlunoDto result) {
		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getNome(), result.getNome());
		assertEquals(expected.getAnoLetivo(), result.getAnoLetivo());		
	}
}
