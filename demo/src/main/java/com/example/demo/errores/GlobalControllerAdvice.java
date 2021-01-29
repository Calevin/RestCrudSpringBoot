package com.example.demo.errores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonMappingException;

@RestControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handleProductoNoEncontrado(NotFoundException ex){
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
	}
	
	@ExceptionHandler(JsonMappingException.class)
	public ResponseEntity<ApiError> handleJsonMappingException(JsonMappingException ex){

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));		
	}
}
