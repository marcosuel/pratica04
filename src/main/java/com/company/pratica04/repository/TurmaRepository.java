package com.company.pratica04.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.company.pratica04.model.Turma;

@Repository
public interface TurmaRepository extends PagingAndSortingRepository<Turma, Long> {

}
