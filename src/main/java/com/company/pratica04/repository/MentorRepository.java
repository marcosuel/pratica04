package com.company.pratica04.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.company.pratica04.model.Mentor;

@Repository
public interface MentorRepository extends PagingAndSortingRepository<Mentor, Long> {

	long countByIdAndMentoradosTurmaId(Long mentor_id, Long turma_id);
	
	Optional<Mentor> findByMatricula(Long matricula);
	
}
