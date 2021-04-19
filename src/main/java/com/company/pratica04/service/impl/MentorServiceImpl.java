package com.company.pratica04.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.mentor.MentorPatchForm;
import com.company.pratica04.dto.mentor.ItemListaMentorDto;
import com.company.pratica04.dto.mentor.MentorDto;
import com.company.pratica04.dto.mentor.MentorPostForm;
import com.company.pratica04.exception.DomainException;
import com.company.pratica04.mapper.AlunoMapper;
import com.company.pratica04.mapper.MentorMapper;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Mentor;
import com.company.pratica04.repository.AlunoRepository;
import com.company.pratica04.repository.MentorRepository;
import com.company.pratica04.service.MentorService;


@Service
public class MentorServiceImpl implements MentorService{
	
	@Autowired
	private MentorRepository mentorRep;
	@Autowired
	private AlunoRepository alunoRep;

	private MentorMapper mapper = MentorMapper.INSTANCE;
	
	@Override
	public MentorDto cadastra(MentorPostForm form) {
		Optional<Mentor> optMentor = mentorRep.findByMatricula(form.getMatricula());
		if(optMentor.isPresent())
			throw new DomainException("A matrícula "+form.getMatricula()+" já foi cadastrada.", HttpStatus.BAD_REQUEST);
		
		Mentor mentor = mapper.toMentor(form);
		mentor = mentorRep.save(mentor);
		
		return mapper.toDto(mentor);
	}
	
	@Override
	public MentorDto atualiza(Long id, MentorPatchForm form) {
		Mentor mentor = garanteQueMentorExiste(id);
		
		Optional<Mentor> optMentor = mentorRep.findByMatricula(form.getMatricula());
		if(optMentor.isPresent() && !optMentor.get().getId().equals(id))
			throw new DomainException("A matrícula "+form.getMatricula()+" já foi cadastrada.", HttpStatus.BAD_REQUEST);
		
		mentor.setNome(form.getNome());
		mentor.setSobrenome(form.getSobrenome());
		mentor.setMatricula(form.getMatricula());
		
		mentor = mentorRep.save(mentor);
		return mapper.toDto(mentor);
	}
	
	@Override
	public Page<ItemListaMentorDto> buscaTodos(Pageable pageable){
		return mentorRep.findAll(pageable).map(mentor -> mapper.toItemListaDto(mentor));
	}
	
	@Override
	public List<AlunoDto> buscaMentorados(Long id){
		Mentor mentor = garanteQueMentorExiste(id);
		List<Aluno> mentorados = mentor.getMentorados();
		return mentorados.stream()
				.map(aluno -> AlunoMapper.INSTANCE.toDto(aluno))
				.collect(Collectors.toList());
	}
	
	@Override
	public MentorDto mentoraAluno(Long idMentor, Long idAluno) {
		Mentor mentor = garanteQueMentorExiste(idMentor);
		
		Optional<Aluno> optAluno = alunoRep.findById(idAluno);
		if(optAluno.isEmpty())	
			throw new DomainException("Não foi encontrado um aluno com o id: "+idAluno, HttpStatus.NOT_FOUND);
		
		Aluno aluno = optAluno.get();
		if(aluno.getTurma() == null)
			throw new DomainException("O mentor não pode mentorar um aluno sem turma.", HttpStatus.BAD_REQUEST);
		if(aluno.getMentor() != null)
			throw new DomainException("O aluno com o id "+idAluno+" já possui um mentor", HttpStatus.BAD_REQUEST);
		
		garanteQuePodeMentorar(idMentor, aluno);
		mentor.getMentorados().add(aluno);
		
		mentor = mentorRep.save(mentor);
		return mapper.toDto(mentor);
	}
	
	@Override
	public MentorDto buscaPorId(Long id) {
		Mentor mentor = garanteQueMentorExiste(id);
		return mapper.toDto(mentor);
	}
	
	@Override
	public void deleta(Long id) {
		if(!mentorRep.existsById(id))
			lancaDomainException(id);
		mentorRep.deleteById(id);
	}
	
	@Override
	public void encerraMentoria(Long idMentor, Long idAluno) {
		Mentor mentor = garanteQueMentorExiste(idMentor);
		
		boolean alunoRemovido = mentor.getMentorados().removeIf(m -> m.getId().equals(idAluno));
		if(!alunoRemovido)
			throw new DomainException("O aluno com id "+idAluno+" não possui mentoria com o mentor de id "
					+idMentor, HttpStatus.BAD_REQUEST);
		
		mentorRep.save(mentor);
	}
	
	private Mentor garanteQueMentorExiste(Long id) {
		Optional<Mentor> optMentor = mentorRep.findById(id);
		if(optMentor.isEmpty())
			lancaDomainException(id);
		return optMentor.get();
	}
	
	private void lancaDomainException(Long id) {
		throw new DomainException("Não foi encontrado um mentor com id: "+id, HttpStatus.NOT_FOUND);
	}

	private void garanteQuePodeMentorar(Long idMentor, Aluno aluno) {
		if(aluno.getTurma() == null)
			throw new DomainException("O mentor não pode iniciar uma mentoria com um aluno sem turma.", HttpStatus.BAD_REQUEST);
		
		Long idTurma = aluno.getTurma().getId();
		long qtd = mentorRep.countByIdAndMentoradosTurmaId(idMentor, idTurma);
		if(qtd >= 3)
			throw new DomainException("O mentor não pode mentorar mais que três alunos da turma com id: "
					+idTurma, HttpStatus.BAD_REQUEST);
	}
}
