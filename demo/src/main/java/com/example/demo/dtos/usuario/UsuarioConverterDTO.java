package com.example.demo.dtos.usuario;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.modelos.Usuario;
import com.example.demo.seguridad.RolUsuario;

@Component
public class UsuarioConverterDTO {
	
	public GetUsuarioDTO convertUsuarioToGetUsuario(Usuario usuario) {
		GetUsuarioDTO getUsuario = new GetUsuarioDTO();
		
		getUsuario.setUsername(usuario.getUsername());
		getUsuario.setAvatar(usuario.getAvatar());
		getUsuario.setRoles(usuario.getRoles().stream().map(RolUsuario::name).collect(Collectors.toSet()));
		
		return getUsuario;
	}

}
