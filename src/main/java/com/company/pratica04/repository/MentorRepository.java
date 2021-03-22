package com.company.pratica04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.pratica04.model.Mentor;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {

	public Mentor save(Mentor mentor);
	
}
