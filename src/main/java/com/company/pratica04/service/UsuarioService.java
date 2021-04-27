package com.company.pratica04.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.company.pratica04.dto.usuario.UsuarioDto;
import com.company.pratica04.dto.usuario.NovaSenhaForm;
import com.company.pratica04.dto.usuario.UsuarioPostForm;

public interface UsuarioService {

	public UsuarioDto cadastra(UsuarioPostForm form);
	public UsuarioDto buscaUsuarioLogado(HttpServletRequest request);
	public void deleta(Long id);
	void atualizaSenha(NovaSenhaForm form, HttpServletRequest request);
	Page<UsuarioDto> buscaTodos(Pageable pageable);
	
}
