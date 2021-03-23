package com.company.pratica04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.company.pratica04.dto.MentorDto;
import com.company.pratica04.form.MentorForm;
import com.company.pratica04.model.Mentor;
import com.company.pratica04.repository.MentorRepository;


@Service
public class MentorService {

	@Autowired
	private MentorRepository repository;
	
	
	public MentorDto save(MentorForm form) {
		
		Mentor mentor = form.convert();
		mentor = repository.save(mentor);
		
		return new MentorDto(mentor);
	}
	
	public Page<MentorDto> findAll(Pageable pageable){
		return repository.findAll(pageable).map(m -> new MentorDto(m));
	}
}
