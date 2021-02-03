package com.example.demo.errores;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	//AuthenticationEntryPoint : Se invoca cuando la autenticación falla
	//Implementación por defecto: BasicAuthenticationEntryPoint
	
	private final ObjectMapper mapperJaxson;

	@Autowired
	public CustomBasicAuthenticationEntryPoint(ObjectMapper mapperJaxson) {
		super();
		this.mapperJaxson = mapperJaxson;
	}
	
	@PostConstruct
	private void initRealmName() {
		setRealmName("restcrudspringboot.com");
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		//La respuesta incluirá una cabecera: WWW-Authenticate: Basic realm="TheRealm"
		response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
		response.setContentType("application/json");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, authException.getMessage()); 
		String stringApiError = mapperJaxson.writeValueAsString(apiError);
		
		PrintWriter writer = response.getWriter();
		writer.println(stringApiError);
	}
}
