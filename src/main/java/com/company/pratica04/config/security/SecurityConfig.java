package com.company.pratica04.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.company.pratica04.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private AutenticacaoService autenticacaoService;
	@Autowired
	private TokenService tokenService;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/alunos").hasAnyAuthority("admin", "secretario")
			.antMatchers(HttpMethod.DELETE, "/alunos/*").hasAnyAuthority("admin", "secretario")
			.antMatchers(HttpMethod.PATCH, "/alunos/*").hasAnyAuthority("admin", "secretario")
			//------------------------------------------------------------------------------------
			.antMatchers(HttpMethod.POST, "/mentores").hasAnyAuthority("admin", "secretario")
			.antMatchers(HttpMethod.POST, "/mentores/*/mentorados").hasAnyAuthority("admin", "mentor")
			.antMatchers(HttpMethod.DELETE, "/mentores/*").hasAnyAuthority("admin", "secretario")
			.antMatchers(HttpMethod.DELETE, "/mentores/*/mentorados/*").hasAnyAuthority("admin", "mentor")
			.antMatchers(HttpMethod.PATCH, "/mentores/*").hasAnyAuthority("admin", "secretario")
			//------------------------------------------------------------------------------------
			.antMatchers(HttpMethod.POST, "/turmas").hasAnyAuthority("admin", "secretario")
			.antMatchers(HttpMethod.POST, "/turmas/*/alunos").hasAnyAuthority("admin", "secretario")
			.antMatchers(HttpMethod.DELETE, "/turmas/*").hasAnyAuthority("admin", "secretario")
			.antMatchers(HttpMethod.DELETE, "/turmas/*/alunos/*").hasAnyAuthority("admin", "secretario")
			.antMatchers(HttpMethod.PATCH, "/turmas/*").hasAnyAuthority("admin", "secretario")
			//------------------------------------------------------------------------------------
			.antMatchers(HttpMethod.GET, "/usuarios").hasAnyAuthority("admin", "secretario")
			.antMatchers(HttpMethod.POST, "/usuarios").hasAnyAuthority("admin", "secretario")
			.antMatchers(HttpMethod.DELETE, "/usuarios/*").hasAnyAuthority("admin", "secretario")
			//------------------------------------------------------------------------------------
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
}
