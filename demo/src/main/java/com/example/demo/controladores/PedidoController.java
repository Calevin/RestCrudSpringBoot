package com.example.demo.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.dtos.pedido.CreatePedidoDTO;
import com.example.demo.dtos.pedido.GetPedidoDTO;
import com.example.demo.dtos.pedido.PedidoConverterDTO;
import com.example.demo.errores.NotFoundException;
import com.example.demo.modelos.Pedido;
import com.example.demo.modelos.Usuario;
import com.example.demo.seguridad.RolUsuario;
import com.example.demo.servicios.PedidoServicio;
import com.example.demo.util.paginacion.PaginacionLinksUtils;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

	private final PedidoServicio pedidoServicio;
	private final PaginacionLinksUtils paginacionLinksUtils;
	private final PedidoConverterDTO pedidoConverterDTO;

	@Autowired
	public PedidoController(PedidoServicio pedidoServicio
			, PaginacionLinksUtils paginacionLinksUtils
			, PedidoConverterDTO pedidoConverterDTO) {
		super();
		this.pedidoServicio = pedidoServicio;
		this.paginacionLinksUtils = paginacionLinksUtils;
		this.pedidoConverterDTO = pedidoConverterDTO;
	}

	@GetMapping("/")
	public ResponseEntity<?> pedidos(Pageable pageable
			, HttpServletRequest request
			, @AuthenticationPrincipal Usuario usuario) {

		Page<Pedido> pedidos = null;
		
		if(usuario.getRoles().contains(RolUsuario.ADMIN)) {
			pedidos = pedidoServicio.findAll(pageable);
		} else {
			pedidos = pedidoServicio.findAllByUser(usuario, pageable);
		}

		if (pedidos.isEmpty()) {
			throw new NotFoundException();
		} else {
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			
			Page<GetPedidoDTO> dtoPage = pedidos.map(pedidoConverterDTO::converterPedidoToGetPedido);

			return ResponseEntity.ok().header("link", paginacionLinksUtils.createLinkHeader(dtoPage, uriBuilder))
					.body(dtoPage);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<GetPedidoDTO> nuevoPedido(@RequestBody CreatePedidoDTO pedido
			, @AuthenticationPrincipal Usuario usuario) {
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(pedidoConverterDTO.converterPedidoToGetPedido(pedidoServicio.nuevoPedido(pedido, usuario)));
	}
}
