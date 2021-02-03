package com.example.demo.dtos.usuario;

import java.util.Set;

public class GetUsuarioDTO {
	private String username;
	private String avatar;
	private Set<String> roles;

	public GetUsuarioDTO(String username, String avatar, Set<String> roles) {
		super();
		this.username = username;
		this.avatar = avatar;
		this.roles = roles;
	}

	public GetUsuarioDTO() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}
