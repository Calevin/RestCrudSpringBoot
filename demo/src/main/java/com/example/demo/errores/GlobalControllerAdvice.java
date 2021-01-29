package com.example.demo.errores;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handleProductoNoEncontrado(NotFoundException ex){
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError  apiError = new ApiError(status, ex.getMessage());
		return ResponseEntity.status(status).headers(headers).body(apiError);
	}
}
