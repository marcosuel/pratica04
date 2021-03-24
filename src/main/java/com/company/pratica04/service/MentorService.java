package com.company.pratica04.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.company.pratica04.dto.mentor.MentorDto;
import com.company.pratica04.dto.mentor.MentorForm;
import com.company.pratica04.exception.DomainException;
import com.company.pratica04.model.Aluno;
import com.company.pratica04.model.Mentor;
import com.company.pratica04.repository.AlunoRepository;
import com.company.pratica04.repository.MentorRepository;


@Service
public class MentorService {

	@Autowired
	private MentorRepository mentorRep;
	
	@Autowired
	private AlunoRepository alunoRep;
	
	
	public MentorDto save(MentorForm form) {
		Optional<Mentor> optMentor = mentorRep.findByMatricula(form.getMatricula());
		if(optMentor.isPresent())
			throw new DomainException("A matrícula "+form.getMatricula()+" já foi cadastrada.", HttpStatus.BAD_REQUEST);
		
		Mentor mentor = form.convert();
		mentor = mentorRep.save(mentor);
		
		return new MentorDto(mentor);
	}
	
	public Page<MentorDto> findAll(Pageable pageable){
		return mentorRep.findAll(pageable).map(m -> new MentorDto(m));
	}
	
	public MentorDto mentorarAluno(Long idMentor, Long idAluno) throws DataIntegrityViolationException {
		Optional<Mentor> optMentor = mentorRep.findById(idMentor);
		if(optMentor.isEmpty())	
			throw new DomainException("Não foi encontrado um mentor com o id: "+idMentor, HttpStatus.NOT_FOUND);
		Optional<Aluno> optAluno = alunoRep.findById(idAluno);
		if(optAluno.isEmpty())	
			throw new DomainException("Não foi encontrado um aluno com o id: "+idAluno, HttpStatus.NOT_FOUND);
		
		Aluno aluno = optAluno.get();
		Long idTurma = aluno.getTurma().getId();
		if(isMentoradosFull(idMentor, idTurma))
			throw new DomainException("O mentor não pode mentorar mais que três alunos da turma com id: "+idTurma, HttpStatus.BAD_REQUEST);

		Mentor mentor = optMentor.get();
		mentor.getMentorados().add(aluno);
		
		try {
			mentor = mentorRep.save(mentor);
		} catch(DataIntegrityViolationException ex) {
			throw new DomainException("O aluno com o id "+idAluno+" já possui um mentor", HttpStatus.BAD_REQUEST);
		}
			
		return new MentorDto(mentor);
	}
	
	private boolean isMentoradosFull(Long idMentor, Long idTurma) {
		long qtd = mentorRep.countByIdAndMentoradosTurmaId(idMentor, idTurma);
		return qtd < 3 ? false : true;
	}
}
