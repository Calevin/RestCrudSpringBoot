package com.example.demo.seguridad.jwt;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.modelos.Usuario;
import com.example.demo.seguridad.RolUsuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component 
public class JwtTokenProvider {
	
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";
	
	@Value("${jwt.secret:EstoEsUnSecretQueTrataDeNoSerDemasiadoCortoParaEvitarUnaWeakKeyException}")
	private String jwtSecreto;
	@Value("${jwt.token-expiration:864000}")
	private int jwtDuracionTokenEnSegundos;

	/**
	 * Generar un token a partir de un Authentication (un usuario logueado)
	 * @param authentication usuario logueado
	 * @return token
	 */
	public String generarToken(Authentication authentication) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		Date tokenExpirationDate = new Date(System.currentTimeMillis() + (jwtDuracionTokenEnSegundos * 1000));
		
		return Jwts.builder()
						// signWith: permite firmar el token 
						// Keys.hmacShaKeyFor(byte[]) : permite generar un SecretKey basado en un array de bytes (listo para ser cifrado).
						.signWith(Keys.hmacShaKeyFor(jwtSecreto.getBytes()), SignatureAlgorithm.HS512)
						// setHeaderParam: permite indicar parámetros para la cabecera del token
						.setHeaderParam("typ", TOKEN_TYPE)
						// setSubject: indica el sujeto (el ID de usuario)
						.setSubject(Long.toString(usuario.getId()))
						// setIssuedAt: indica la fecha de creación del token
						.setIssuedAt(new Date())
						// setExpiration: indica la fecha de expiración del token 
						.setExpiration(tokenExpirationDate)
						// claim: permite indicar datos adicionales para el payload. 
						.claim("nombre", usuario.getUsername())
						.claim("roles", usuario
											.getRoles().stream()
											.map(RolUsuario::name)
											.collect(Collectors.joining(", ")))
						// compact: construye el token y lo serializa
						.compact();
	}
	
	/**
	 * Obtener el ID de usuario a partir del payload de un token
	 * @param token
	 * @return ID del usuario
	 */
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
								.setSigningKey(Keys.hmacShaKeyFor(jwtSecreto.getBytes()))
								.parseClaimsJws(token)
								.getBody();
		
		return Long.parseLong(claims.getSubject());
	}
	
	/**
	 * Verificar si un token es valido.
	 * @param authToken
	 * @return
	 */
	public boolean validateToken(String authToken) {
		
		try {
			Jwts.parser().setSigningKey(jwtSecreto.getBytes()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			System.err.println("Error en la firma del token JWT: " + ex.getMessage());
		} catch (MalformedJwtException ex) {
			System.err.println("Token malformado: " + ex.getMessage());
		} catch (ExpiredJwtException  ex) {
			System.err.println("El token ha expirado: " + ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			System.err.println("Token JWT no soportado: " + ex.getMessage());
		} catch (IllegalArgumentException  ex) {
			System.err.println("WT claims vacío " + ex.getMessage());
		}
		
        return false;
	}
}
