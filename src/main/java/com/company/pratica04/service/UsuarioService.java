package com.company.pratica04.service;

import javax.servlet.http.HttpServletRequest;

import com.company.pratica04.dto.usuario.UsuarioDto;
import com.company.pratica04.dto.usuario.UsuarioPostForm;

public interface UsuarioService {

	public UsuarioDto cadastra(UsuarioPostForm form);
	public UsuarioDto buscaUsuarioLogado(HttpServletRequest request);
}
