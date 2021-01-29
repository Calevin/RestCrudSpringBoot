package com.example.demo.errores;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -5560878765864962301L;

	public NotFoundException(Long id) {
		super("No se pudo encontrar registro con ID: " + id);
	}
}
