package com.company.pratica04.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.company.pratica04.config.security.TokenService;
import com.company.pratica04.dto.usuario.UsuarioDto;
import com.company.pratica04.dto.usuario.UsuarioPostForm;
import com.company.pratica04.exception.DomainException;
import com.company.pratica04.mapper.UsuarioMapper;
import com.company.pratica04.model.Perfil;
import com.company.pratica04.model.Usuario;
import com.company.pratica04.repository.PerfilRepository;
import com.company.pratica04.repository.UsuarioRepository;
import com.company.pratica04.service.UsuarioService;
import com.company.pratica04.util.Autorities;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository userRep;
	@Autowired
	private PerfilRepository perfilRep;
	@Autowired
	private TokenService tokenService;
	
	private UsuarioMapper mapper = UsuarioMapper.INSTANCE;

	@Override
	public UsuarioDto cadastra(UsuarioPostForm form) {
		var usuario = mapper.toUsuario(form);
		var listaPerfis = form.getListaPerfis(); 
		var perfis = new ArrayList<Perfil>();
		
		Optional<Usuario> usuarioOpt = userRep.findByEmail(form.getEmail());
		if(usuarioOpt.isPresent())
			throw new DomainException("Email já cadastrado.", HttpStatus.BAD_REQUEST);
		if(listaPerfis.contains(Autorities.ADMIN.getCode()))
			throw new DomainException("Access Denied", HttpStatus.FORBIDDEN);
		listaPerfis.forEach(perfil -> {
			var temp = perfilRep.findById(perfil);
			temp.ifPresent(perfis::add);
		});
		
		usuario.setPerfis(perfis);
		usuario = userRep.save(usuario);

		return mapper.toDto(usuario);
	}

	@Override
	public UsuarioDto buscaUsuarioLogado(HttpServletRequest request) {
		String token = tokenService.recuperarToken(request);
		Long idUsuario = tokenService.getIdUsuario(token);
		Optional<Usuario> usuarioOpt = userRep.findById(idUsuario);
		return mapper.toDto(usuarioOpt.get());
	}

	@Override
	public void deleta(Long id) {
		Usuario usuario = garanteQueUsuarioExiste(id);
		usuario.getAuthorities().forEach(a -> {
			var perfil = (Perfil) a;
			if(perfil.getId().equals(Autorities.ADMIN.getCode()))
				throw new DomainException("Access Denied", HttpStatus.FORBIDDEN);
		});
		userRep.delete(usuario);
	}
	
	private Usuario garanteQueUsuarioExiste(Long id) {
		Optional<Usuario> usuarioOpt = userRep.findById(id);
		if(usuarioOpt.isEmpty())
			throw new DomainException("Não existe um usuário com o id: " + id, HttpStatus.NOT_FOUND);
		return usuarioOpt.get();
	}
	
}
