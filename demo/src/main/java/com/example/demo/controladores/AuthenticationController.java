package com.example.demo.controladores;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.usuario.GetUsuarioDTO;
import com.example.demo.dtos.usuario.UsuarioConverterDTO;
import com.example.demo.modelos.Usuario;
import com.example.demo.seguridad.RolUsuario;
import com.example.demo.seguridad.jwt.JwtTokenProvider;
import com.example.demo.seguridad.jwt.modelo.JwtUsuarioResponse;
import com.example.demo.seguridad.jwt.modelo.LoginRequest;

@RestController
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider tokenProvider;
	private final UsuarioConverterDTO usuarioConverterDTO;
	
	@Autowired 
	public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
			UsuarioConverterDTO usuarioConverterDTO) {
		super();
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
		this.usuarioConverterDTO = usuarioConverterDTO;
	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<JwtUsuarioResponse> login(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = 
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Usuario usuario = (Usuario) authentication.getPrincipal();
		String jwtToken = tokenProvider.generarToken(authentication);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(convertUsuarioToJwtUsuarioResponse(usuario, jwtToken));
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/me_jwt")
	public GetUsuarioDTO me(@AuthenticationPrincipal Usuario usuario) {
		return usuarioConverterDTO.convertUsuarioToGetUsuario(usuario);
	}
	
	//TODO mover a usuarioConverterDTO???
	private JwtUsuarioResponse convertUsuarioToJwtUsuarioResponse(Usuario usuario, String jwtToken) {
		JwtUsuarioResponse jwtUsuarioResponse = new JwtUsuarioResponse();
		jwtUsuarioResponse.setUsername(usuario.getUsername());
		jwtUsuarioResponse.setAvatar(usuario.getAvatar());
		jwtUsuarioResponse.setRoles(usuario.getRoles().stream().map(RolUsuario::name).collect(Collectors.toSet()));
		jwtUsuarioResponse.setToken(jwtToken); 
		
		return jwtUsuarioResponse;
	}
}
