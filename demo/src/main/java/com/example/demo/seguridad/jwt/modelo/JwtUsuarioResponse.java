package com.example.demo.seguridad.jwt.modelo;

import java.util.Set;

import com.example.demo.dtos.usuario.GetUsuarioDTO;

public class JwtUsuarioResponse extends GetUsuarioDTO {
	
	private String token;
	
	public JwtUsuarioResponse() {
		super();
	}

	public JwtUsuarioResponse(String username, String avatar, Set<String> roles, String token) {
		super(username, avatar, roles);
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
