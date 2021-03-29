package com.company.pratica04.repository;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.pratica04.model.Aluno;

@Repository
public interface AlunoRepository extends PagingAndSortingRepository<Aluno, Long>{

	Page<Aluno> findAll(Pageable pageable);

	Optional<Aluno> findByMatricula(Long matricula);
}
