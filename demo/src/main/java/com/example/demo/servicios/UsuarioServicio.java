package com.example.demo.servicios;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.usuario.CreateUsuarioDTO;
import com.example.demo.errores.PasswordNoCoincideException;
import com.example.demo.modelos.Usuario;
import com.example.demo.repositorios.UsuarioRepositorio;
import com.example.demo.seguridad.RolUsuario;
import com.example.demo.servicios.base.BaseService;

@Service
public class UsuarioServicio extends BaseService<Usuario, Long, UsuarioRepositorio> {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UsuarioServicio(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}

	public Optional<Usuario> findByUsername(String username) {
		return this.repositorio.findByUsername(username);
	}

	public Usuario nuevoUsuario(CreateUsuarioDTO createusuario) {
		Usuario usuarioAguardar = new Usuario();
		
		if(createusuario.getPassword().contentEquals(createusuario.getPasswordConfirmacion())) {
			usuarioAguardar.setUsername(createusuario.getUsername());
			usuarioAguardar.setPassword(passwordEncoder.encode(createusuario.getPassword()));
			usuarioAguardar.setAvatar(createusuario.getAvatar());
			usuarioAguardar.setRoles(Stream.of(RolUsuario.USER).collect(Collectors.toSet()));
			
			return this.save(usuarioAguardar);
			
		} else {
			throw new PasswordNoCoincideException();
		}		
	}
}
