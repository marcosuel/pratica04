package com.company.pratica04.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.mentor.ItemListaMentorDto;
import com.company.pratica04.dto.mentor.MentorDto;
import com.company.pratica04.dto.mentor.MentorPatchForm;
import com.company.pratica04.dto.mentor.MentorPostForm;

public interface MentorService {
	
	public MentorDto cadastra(MentorPostForm form);
	public MentorDto atualiza(Long id, MentorPatchForm form);
	public Page<ItemListaMentorDto> buscaTodos(Pageable pageable);
	public List<AlunoDto> buscaMentorados(Long id);
	public MentorDto mentoraAluno(Long idMentor, Long idAluno);
	public MentorDto buscaPorId(Long id);
	public void deleta(Long id);
	public void encerraMentoria(Long idMentor, Long idAluno);
	
}
