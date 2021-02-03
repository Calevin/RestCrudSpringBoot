package com.example.demo.errores;

public class PasswordNoCoincideException extends RuntimeException {

	private static final long serialVersionUID = -722895978694120950L;

	public PasswordNoCoincideException() {
		super("Las contraseñas no coinciden");
	}
}
