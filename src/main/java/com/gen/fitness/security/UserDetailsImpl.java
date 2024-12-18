package com.gen.fitness.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gen.fitness.model.Usuario;

public class UserDetailsImpl implements UserDetails{

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private List<GrantedAuthority> roles;
	
	public UserDetailsImpl() {
	}

	public UserDetailsImpl(Usuario usuario) {
		this.username = usuario.getUsuario();
		this.password = usuario.getSenha();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}
}
