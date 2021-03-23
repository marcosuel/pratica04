package com.company.pratica04.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.company.pratica04.model.Aluno;

@Repository
public interface AlunoRepository extends PagingAndSortingRepository<Aluno, Long>{

	Page<Aluno> findAll(Pageable pageable);
	
}
