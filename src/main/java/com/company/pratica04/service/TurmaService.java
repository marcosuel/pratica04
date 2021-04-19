package com.company.pratica04.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.company.pratica04.dto.aluno.AlunoItemListaTurmaDto;
import com.company.pratica04.dto.turma.TurmaDto;
import com.company.pratica04.dto.turma.TurmaItemListaDto;
import com.company.pratica04.dto.turma.TurmaPatchForm;
import com.company.pratica04.dto.turma.TurmaPostForm;

public interface TurmaService {

	public TurmaDto cadastra(TurmaPostForm form);
	public TurmaItemListaDto atualiza(Long id, TurmaPatchForm form);
	public Page<TurmaItemListaDto> buscaTodos(Pageable pageable);
	public List<AlunoItemListaTurmaDto> listaAlunos(Long id);
	public TurmaDto buscaPorId(Long id);
	public void deletaPorId(Long id);
	public AlunoItemListaTurmaDto adicionaAluno(Long idTurma, Long idAluno);
	public void removeAluno(Long idTurma, Long idAluno);
	
}
