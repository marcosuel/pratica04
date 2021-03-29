package com.company.pratica04.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.company.pratica04.dto.turma.TurmaItemListaDto;
import com.company.pratica04.dto.aluno.AlunoItemListaTurmaDto;
import com.company.pratica04.dto.turma.TurmaDto;
import com.company.pratica04.dto.turma.TurmaForm;
import com.company.pratica04.exception.DomainException;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Turma;
import com.company.pratica04.repository.TurmaRepository;

@Service
public class TurmaService {

	@Autowired
	private TurmaRepository turmaRep;
	@Autowired
	private AlunoService alunoService;
	
	public TurmaDto cadastra(TurmaForm form) {
		Turma turma = form.convert();
		turma = turmaRep.save(turma);
		
		return new TurmaDto(turma);
	}
	
	public TurmaItemListaDto atualiza(Long id, TurmaForm form) {
		Turma turma = garanteQueTurmaExiste(id);
		
		turma.setNome(form.getNome());
		turma.setAnoLetivo(form.getAnoLetivo());
		
		turma = turmaRep.save(turma);
		return new TurmaItemListaDto(turma);
	}
	
	public Page<TurmaItemListaDto> buscaTodos(Pageable pageable) {
		return turmaRep.findAll(pageable).map(t -> new TurmaItemListaDto(t));
	}
	
	public TurmaDto buscaPorId(Long id) {
		Turma turma = garanteQueTurmaExiste(id);
		return new TurmaDto(turma);
	}
	
	public void deletaPorId(Long id) {
		Turma turma = garanteQueTurmaExiste(id);
		turma.getAlunos().forEach(aluno -> aluno.setMentor(null));
		
		turmaRep.delete(turma);
	}
	
	public AlunoItemListaTurmaDto adicionaAluno(Long idTurma, Long idAluno) {
		Turma turma = garanteQueTurmaExiste(idTurma);
		Aluno aluno = alunoService.garanteQueAlunoExiste(idAluno);
		
		if(aluno.getTurma() != null)
			throw new DomainException("O aluno com id "+idAluno+" já está em uma turma."
					, HttpStatus.BAD_REQUEST);
			
		turma.getAlunos().add(aluno);
		turma.setQuantidadeAlunos(turma.getQuantidadeAlunos()+1);
		turmaRep.save(turma);
		return new AlunoItemListaTurmaDto(aluno);
	}
	
	public void removeAluno(Long idTurma, Long idAluno) {
		Turma turma = garanteQueTurmaExiste(idTurma);
		Aluno aluno = alunoService.garanteQueAlunoExiste(idAluno);
	
		List<Aluno> alunos = turma.getAlunos();
		aluno.setMentor(null);
		alunos.remove(aluno);
		turma.setQuantidadeAlunos(turma.getQuantidadeAlunos()-1);
		turmaRep.save(turma);
	}
	
	private Turma garanteQueTurmaExiste(Long id) {
		Optional<Turma> optTurma = turmaRep.findById(id);
		if(optTurma.isEmpty())
			lancaDomainException(id);
		return optTurma.get();
	}
	
	private void lancaDomainException(Long id) {
		throw new DomainException("Não foi encontrada uma turma com id: "+id, HttpStatus.NOT_FOUND);
	}
}
