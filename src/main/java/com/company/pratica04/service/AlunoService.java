package com.company.pratica04.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.company.pratica04.dto.aluno.AlunoDto;
import com.company.pratica04.dto.aluno.AlunoPatchForm;
import com.company.pratica04.dto.aluno.AlunoPostForm;

public interface AlunoService {

	public AlunoDto cadastra(AlunoPostForm form);
	public Page<AlunoDto> buscaTodos(Pageable pageable);
	public AlunoDto buscaPorId(Long id);
	public void deleta(Long id);
	public AlunoDto atualiza(Long id, AlunoPatchForm form);
}
