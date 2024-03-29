package com.company.pratica04.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.company.pratica04.dto.turma.TurmaItemListaDto;
import com.company.pratica04.dto.turma.TurmaPatchForm;
import com.company.pratica04.dto.aluno.AlunoItemListaTurmaDto;
import com.company.pratica04.dto.turma.TurmaDto;
import com.company.pratica04.dto.turma.TurmaPostForm;
import com.company.pratica04.exception.DomainException;
import com.company.pratica04.mapper.AlunoMapper;
import com.company.pratica04.mapper.TurmaMapper;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Turma;
import com.company.pratica04.repository.TurmaRepository;
import com.company.pratica04.service.TurmaService;

@Service
public class TurmaServiceImpl implements TurmaService{

	@Autowired
	private TurmaRepository turmaRep;
	@Autowired
	private AlunoServiceImpl alunoService;

	private TurmaMapper mapper = TurmaMapper.INSTANCE;
	
	@Override
	public TurmaDto cadastra(TurmaPostForm form) {
		Optional<Turma> optTurma = turmaRep.findByAnoLetivo(form.getAnoLetivo());
		if(optTurma.isPresent())
			throw new DomainException("A turma do ano de "+form.getAnoLetivo()+" já foi cadastrada.", HttpStatus.BAD_REQUEST);
		Turma turma = mapper.toTurma(form);
		turma = turmaRep.save(turma);
		
		return mapper.toDto(turma);
	}
	
	@Override
	public TurmaItemListaDto atualiza(Long id, TurmaPatchForm form) {
		Turma turma = garanteQueTurmaExiste(id);
		Optional<Turma> optTurma = turmaRep.findByAnoLetivo(form.getAnoLetivo());
		if(optTurma.isPresent())
			throw new DomainException("Não podem existir duas turmas com o mesmo ano: "+form.getAnoLetivo()+".", HttpStatus.BAD_REQUEST);
		
		turma.setNome(form.getNome());
		turma.setAnoLetivo(form.getAnoLetivo());
		
		turma = turmaRep.save(turma);
		return mapper.toItemListaDto(turma);
	}
	
	@Override
	public Page<TurmaItemListaDto> buscaTodos(Pageable pageable) {
		return turmaRep.findAll(pageable).map(mapper::toItemListaDto);
	}
	
	@Override
	public List<AlunoItemListaTurmaDto> listaAlunos(Long id){
		Turma turma = garanteQueTurmaExiste(id);
		List<Aluno> alunos = turma.getAlunos();
		return alunos.stream()
				.map(aluno -> AlunoMapper.INSTANCE.toItemTurmaDto(aluno))
				.collect(Collectors.toList());
	}
	
	@Override
	public TurmaDto buscaPorId(Long id) {
		Turma turma = garanteQueTurmaExiste(id);
		return mapper.toDto(turma);
	}
	
	@Override
	public void deletaPorId(Long id) {
		Turma turma = garanteQueTurmaExiste(id);
		turma.getAlunos().forEach(aluno -> aluno.setMentor(null));
		
		turmaRep.delete(turma);
	}
	
	@Override
	public AlunoItemListaTurmaDto adicionaAluno(Long idTurma, Long idAluno) {
		Turma turma = garanteQueTurmaExiste(idTurma);
		Aluno aluno = alunoService.garanteQueAlunoExiste(idAluno);
		
		if(aluno.getTurma() != null)
			throw new DomainException("O aluno com id "+idAluno+" já está em uma turma."
					, HttpStatus.BAD_REQUEST);
			
		turma.getAlunos().add(aluno);
		turma.setQuantidadeAlunos(turma.getQuantidadeAlunos()+1);
		turmaRep.save(turma);
		return AlunoMapper.INSTANCE.toItemTurmaDto(aluno);
	}
	
	@Override
	public void removeAluno(Long idTurma, Long idAluno) {
		Turma turma = garanteQueTurmaExiste(idTurma);
		Aluno aluno = alunoService.garanteQueAlunoExiste(idAluno);
		
		List<Aluno> alunos = turma.getAlunos();
		aluno.setMentor(null);
		if(!alunos.remove(aluno))
			throw new DomainException("O aluno com id "+aluno.getId()+" não pertence a esta turma.", HttpStatus.BAD_REQUEST);
		
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
