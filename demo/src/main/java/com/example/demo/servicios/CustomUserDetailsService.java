package com.example.demo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private final UsuarioServicio usuarioServicio;

	@Autowired
	public CustomUserDetailsService(UsuarioServicio usuarioServicio) {
		super();
		this.usuarioServicio = usuarioServicio;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.usuarioServicio
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " no encontrado"));
	}

}
