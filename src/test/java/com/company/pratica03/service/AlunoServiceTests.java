package com.company.pratica03.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.Year;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.aluno.AlunoPostForm;
import com.company.pratica04.dto.turma.TurmaItemAlunoDto;
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
	
	@InjectMocks
	private AlunoService service;
	
	@Test
	void testaCadastroAlunoComSucesso() {
		//setting up attributes
		Long idAluno = 1L;
		Long idTurma = 2L;
		String nomeAluno = "José";
		String sobrenome = "Alberto";
		String nomeTurma = "Turma de POO";
		Long matricula = 542184L;
		Year ano = Year.of(2017);
		int qtdAluno = 0;
		//setting up objects
		AlunoPostForm form = new AlunoPostForm(nomeAluno, sobrenome, matricula, idTurma);
		Turma turma = new Turma(idTurma, nomeTurma, qtdAluno, ano, new ArrayList<Aluno>());
		Aluno unsaved = new Aluno();
		unsaved.setNome(nomeAluno);
		unsaved.setSobrenome(sobrenome);
		unsaved.setTurma(turma);
		unsaved.setMatricula(matricula);
		Aluno aluno = new Aluno(idAluno, nomeAluno, sobrenome, matricula, turma, null);
		TurmaItemAlunoDto turmaItemDto = new TurmaItemAlunoDto(idTurma, nomeTurma, ano);
		AlunoDto expected = new AlunoDto(idAluno, nomeAluno, sobrenome, matricula, turmaItemDto);
		//setting up mocks
		when(alunoRep.findByMatricula(matricula)).thenReturn(Optional.empty());
		when(turmaRep.findById(idTurma)).thenReturn(Optional.of(turma));
		when(alunoRep.save(unsaved)).thenReturn(aluno);
		//running code
		AlunoDto result = service.cadastra(form);
		//running asserts
		TurmaItemAlunoDto turmaResult = result.getTurma();
		assertEquals(turmaItemDto.getId(), turmaResult.getId());
		assertEquals(turmaItemDto.getNome(), turmaResult.getNome());
		assertEquals(turmaItemDto.getAnoLetivo(), turmaResult.getAnoLetivo());
		assertEquals(qtdAluno+1, turma.getQuantidadeAlunos());

		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getNome(), result.getNome());
		assertEquals(expected.getSobrenome(), result.getSobrenome());
		assertEquals(expected.getMatricula(), result.getMatricula());
	}
	
	

}
