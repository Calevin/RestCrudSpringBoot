package com.example.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dtos.usuario.CreateUsuarioDTO;
import com.example.demo.dtos.usuario.GetUsuarioDTO;
import com.example.demo.dtos.usuario.UsuarioConverterDTO;
import com.example.demo.modelos.Usuario;
import com.example.demo.servicios.UsuarioServicio;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final UsuarioServicio usuarioServicio;
	private final UsuarioConverterDTO usuarioConverterDTO;

	@Autowired
	public UsuarioController(UsuarioServicio usuarioServicio
			, UsuarioConverterDTO usuarioConverterDTO) {
		super();
		this.usuarioServicio = usuarioServicio;
		this.usuarioConverterDTO = usuarioConverterDTO;
	}

	@PostMapping("/")
	public ResponseEntity<GetUsuarioDTO> nuevoUsuario(@RequestBody CreateUsuarioDTO createusuario){
		try {
			
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(usuarioConverterDTO.convertUsuarioToGetUsuario(usuarioServicio.nuevoUsuario(createusuario)));
			
		} catch (DataIntegrityViolationException ex) {
			//TODO manejo de error en el servicio
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	@GetMapping("/me")
	public GetUsuarioDTO yo(@AuthenticationPrincipal Usuario usuario) {
		return usuarioConverterDTO.convertUsuarioToGetUsuario(usuario);
	}
}
