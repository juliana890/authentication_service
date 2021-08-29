package com.gptw.authenticationservice.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gptw.authenticationservice.entities.Cliente;

//Classe contrato implementa a classe UserDetails do Spring Security
public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Optional<Cliente> cliente;
	
	public UserSS() {}
	
	public UserSS(Optional<Cliente> cliente)  {
		this.cliente = cliente;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return new ArrayList<>();
	}
	
	@Override
	public String getUsername() {
		return cliente.orElse(new Cliente()).getEmail();
	}
	
	@Override
	public String getPassword() {
		return cliente.orElse(new Cliente()).getSenha();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
