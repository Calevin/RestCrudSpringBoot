package com.example.demo.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.errores.NotFoundException;
import com.example.demo.modelos.Pedido;
import com.example.demo.servicios.PedidoServicio;
import com.example.demo.util.paginacion.PaginacionLinksUtils;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

	private final PedidoServicio pedidoServicio;
	private final PaginacionLinksUtils paginacionLinksUtils;

	@Autowired
	public PedidoController(PedidoServicio pedidoServicio, PaginacionLinksUtils paginacionLinksUtils) {
		super();
		this.pedidoServicio = pedidoServicio;
		this.paginacionLinksUtils = paginacionLinksUtils;
	}

	@GetMapping("/")
	public ResponseEntity<?> pedidos(Pageable pageable, HttpServletRequest request) {

		Page<Pedido> pedidos = pedidoServicio.findAll(pageable);

		if (pedidos.isEmpty()) {
			throw new NotFoundException();
		} else {
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

			return ResponseEntity.ok().header("link", paginacionLinksUtils.createLinkHeader(pedidos, uriBuilder))
					.body(pedidos);
		}
	}
}
