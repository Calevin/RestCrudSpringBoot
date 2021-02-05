package com.example.demo.seguridad.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.modelos.Usuario;
import com.example.demo.servicios.CustomUserDetailsService;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	//OncePerRequestFilter: filtro que va a ejecutarse una vez en cada peticion.
	
	private final JwtTokenProvider tokenProvider;
	private final CustomUserDetailsService customUserDetailsService; 
	
	
	@Autowired
	public JwtAuthorizationFilter(JwtTokenProvider tokenProvider
			, CustomUserDetailsService customUserDetailsService) {
		super();
		this.tokenProvider = tokenProvider;
		this.customUserDetailsService = customUserDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			//Extraemos el token de la peticion 
			String token = getJwtFromRequest(request);
			
			// Si el token no es vacío y es válido
			if(StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
				
				// Obtenemos el ID de usuario del token 
				Long userId = tokenProvider.getUserIdFromJWT(token);
				
				// Obtenemos el usuario por su ID 
				Usuario usuario = (Usuario) customUserDetailsService.loadUserById(userId);
				
				// Construimos un Authentication 
				// UsernamePasswordAuthenticationToken: una representación de Authentication muy simple
				// para presentar username y password. 
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(usuario, usuario.getRoles(), usuario.getAuthorities());
				
				authentication.setDetails(new WebAuthenticationDetails(request));
				
				// Si la autenticación se realiza correctamente (en nuestro caso, si el token es validado)
				// , debemos almacenar en el contexto de seguridad una instancia de dicha clase:
				
				// Lo establecemos en el contexto de seguridad
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
		} catch (Exception e) {
			// Error: la cadena de filtros no continua
			System.out.println("No se ha podido establecer la autenticacion de usuario en el contexto de seguridad");
		}
		
		filterChain.doFilter(request, response);
	}

	/**
	 * Extrae el token de la petición
	 * @param request
	 * @return token
	 */
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(JwtTokenProvider.TOKEN_HEADER);
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
			return bearerToken.substring(JwtTokenProvider.TOKEN_PREFIX.length(), bearerToken.length());
		}
		
		return null;
	}

}
