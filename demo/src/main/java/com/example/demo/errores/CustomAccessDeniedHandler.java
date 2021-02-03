package com.example.demo.errores;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	//Para manejo de error: 403 Forbidden
	
	private final ObjectMapper mapperJaxson;

	@Autowired
	public CustomAccessDeniedHandler(ObjectMapper mapperJaxson) {
		super();
		this.mapperJaxson = mapperJaxson;
	}
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		response.setContentType("application/json");
		response.setStatus(HttpStatus.FORBIDDEN.value());
		
		ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, accessDeniedException.getMessage()); 
		String stringApiError = mapperJaxson.writeValueAsString(apiError);
		
		PrintWriter writer = response.getWriter();
		writer.println(stringApiError);
	}
}
