package com.company.pratica03.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
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
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.pratica04.dto.aluno.AlunoDto;
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
	Aluno alunoNaoSalvo;
	Aluno alunoSalvo;
	Turma turma;
	TurmaItemAlunoDto turmaItem;
	AlunoDto alunoDto;
	
	@BeforeEach
	public void init() {
		//setting up attributes
		var idAluno = 1L;
		var idTurma = 2L;
		var nomeAluno = "José";
		var sobrenome = "Alberto";
		var nomeTurma = "Turma de POO";
		var matricula = 542184L;
		var ano = Year.of(2017);
		var qtdAlunos = 1;

		//setting up objects
		alunoPostForm = new AlunoPostForm(nomeAluno, sobrenome, matricula, idTurma);
		turma = new Turma(idTurma, nomeTurma, qtdAlunos, ano, new ArrayList<Aluno>());
		alunoSalvo = new Aluno(idAluno, nomeAluno, sobrenome, matricula, turma, null);
		alunoNaoSalvo = new Aluno(nomeAluno, sobrenome, matricula, turma);
		turmaItem = new TurmaItemAlunoDto(idTurma, nomeTurma, ano);
		alunoDto = new AlunoDto(alunoSalvo.getId(), alunoSalvo.getNome(), alunoSalvo.getSobrenome(), alunoSalvo.getMatricula(), turmaItem);
	}
	
	@Test
	void testaCadastroAlunoComSucesso() {
		var qtdExcepted = turma.getQuantidadeAlunos();
		
		//setting up mocks
		when(alunoRep.findByMatricula(alunoPostForm.getMatricula())).thenReturn(Optional.empty());
		when(turmaRep.findById(turma.getId())).thenReturn(Optional.of(turma));
		when(alunoRep.save(alunoNaoSalvo)).thenReturn(alunoSalvo);
		when(alunoMapper.toAluno(alunoPostForm)).thenReturn(alunoNaoSalvo);
		when(alunoMapper.toDto(alunoSalvo)).thenReturn(alunoDto);
		
		//running code
		var result = service.cadastra(alunoPostForm);
		var turmaResult = result.getTurma();
		
		var expected = new AlunoDto(alunoSalvo.getId(), alunoSalvo.getNome(), alunoSalvo.getSobrenome(), alunoSalvo.getMatricula(), turmaItem);
		var turmaExpected = new TurmaItemAlunoDto(turmaItem.getId(), turmaItem.getNome(), turmaItem.getAnoLetivo());
		
		//running asserts
		assertEquals(qtdExcepted+1, turma.getQuantidadeAlunos());
		comparaItemTurmaEsperadoComResultado(turmaExpected, turmaResult);
		comparaAlunoDtoEsperadoComResultado(expected, result);
	}
	
	@Test
	void testaCadastroAlunoComTurmaNaoExistenteFalha() {
		var id = 999L;
		alunoPostForm.setIdTurma(id);
		when(turmaRep.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, () -> {
			service.cadastra(alunoPostForm);
		});
	}
	
	@Test
	void testaCadastroAlunoComMatriculaRepetidaFalha() {
		when(alunoRep.findByMatricula(alunoPostForm.getMatricula())).thenReturn(Optional.of(alunoSalvo));
		when(turmaRep.findById(alunoPostForm.getIdTurma())).thenReturn(Optional.of(turma));
		
		assertThrows(DomainException.class, () -> {
			service.cadastra(alunoPostForm);
		});
	}
	
	@Test
	void testaBuscaAlunoPorIdComSucesso() {
		var id = alunoSalvo.getId();
		when(alunoRep.findById(id)).thenReturn(Optional.of(alunoSalvo));
		
		var result = service.buscaPorId(id);
		var expected = new AlunoDto(alunoSalvo.getId(), alunoSalvo.getNome(), alunoSalvo.getSobrenome(), alunoSalvo.getMatricula(), turmaItem);
		
		comparaAlunoDtoEsperadoComResultado(expected, result);
	}
	
	@Test
	void testaBuscaAlunoPorIdComIdInexistente() {
		var id = 999L;
		when(alunoRep.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(DomainException.class, () -> {
			service.buscaPorId(id);
		});
	}
	
	@Test
	void deletaAlunoComSucesso() {
		var qtdExcepted = turma.getQuantidadeAlunos()-1;
		var id = alunoSalvo.getId();
		when(alunoRep.findById(id)).thenReturn(Optional.of(alunoSalvo));
		service.deleta(id);
		
		verify(alunoRep, times(1)).delete(alunoSalvo);
		assertEquals(qtdExcepted, turma.getQuantidadeAlunos());
	}
	
	private void comparaAlunoDtoEsperadoComResultado(AlunoDto expected, AlunoDto result) {
		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getNome(), result.getNome());
		assertEquals(expected.getSobrenome(), result.getSobrenome());
		assertEquals(expected.getMatricula(), result.getMatricula());		
	}
	
	private void comparaItemTurmaEsperadoComResultado(TurmaItemAlunoDto expected, TurmaItemAlunoDto result) {
		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getNome(), result.getNome());
		assertEquals(expected.getAnoLetivo(), result.getAnoLetivo());		
	}
}
