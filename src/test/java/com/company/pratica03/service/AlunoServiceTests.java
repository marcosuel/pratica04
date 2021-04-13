package com.company.pratica03.service;

import static org.junit.jupiter.api.Assertions.*;
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

	AlunoPostForm alunoPostForm;
	Aluno alunoNaoSalvo;
	Aluno alunoSalvo;
	Turma turma;
	TurmaItemAlunoDto turmaItem;
	AlunoDto alunoDto;
	int qtdAlunos;
	
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
		qtdAlunos = 1;

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
		//setting up mocks
		when(alunoRep.findByMatricula(alunoPostForm.getMatricula())).thenReturn(Optional.empty());
		when(turmaRep.findById(turma.getId())).thenReturn(Optional.of(turma));
		when(alunoRep.save(alunoNaoSalvo)).thenReturn(alunoSalvo);
		when(alunoMapper.toAluno(alunoPostForm)).thenReturn(alunoNaoSalvo);
		when(alunoMapper.toDto(alunoSalvo)).thenReturn(alunoDto);
		
		AlunoDto expected = new AlunoDto(alunoSalvo.getId(), alunoSalvo.getNome(), alunoSalvo.getSobrenome(), alunoSalvo.getMatricula(), turmaItem);
		//running code
		AlunoDto result = service.cadastra(alunoPostForm);
		//running asserts
		TurmaItemAlunoDto turmaResult = result.getTurma();
		assertEquals(turmaItem.getId(), turmaResult.getId());
		assertEquals(turmaItem.getNome(), turmaResult.getNome());
		assertEquals(turmaItem.getAnoLetivo(), turmaResult.getAnoLetivo());
		assertEquals(qtdAlunos+1, turma.getQuantidadeAlunos());

		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getNome(), result.getNome());
		assertEquals(expected.getSobrenome(), result.getSobrenome());
		assertEquals(expected.getMatricula(), result.getMatricula());
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
	
	
	
}
