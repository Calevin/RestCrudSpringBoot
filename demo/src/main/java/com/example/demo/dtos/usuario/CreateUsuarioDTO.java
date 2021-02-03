package com.example.demo.dtos.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUsuarioDTO {

	private String username;
	private String avatar;
	private String password;
	@JsonProperty("password_confirmacion")
	private String passwordConfirmacion;

	public CreateUsuarioDTO(String username, String avatar, String password, String passwordConfirmacion) {
		super();
		this.username = username;
		this.avatar = avatar;
		this.password = password;
		this.passwordConfirmacion = passwordConfirmacion;
	}

	public CreateUsuarioDTO() {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmacion() {
		return passwordConfirmacion;
	}

	public void setPasswordConfirmacion(String passwordConfirmacion) {
		this.passwordConfirmacion = passwordConfirmacion;
	}
}
