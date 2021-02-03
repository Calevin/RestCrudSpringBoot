package com.example.demo.servicios;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.pedido.CreatePedidoDTO;
import com.example.demo.modelos.LineaPedido;
import com.example.demo.modelos.Pedido;
import com.example.demo.modelos.Producto;
import com.example.demo.modelos.Usuario;
import com.example.demo.repositorios.PedidoRepositorio;
import com.example.demo.servicios.base.BaseService;

@Service 
public class PedidoServicio extends BaseService<Pedido, Long, PedidoRepositorio> {

	private final ProductoServicio productoServicio;

	@Autowired  
	public PedidoServicio(ProductoServicio productoServicio) {
		super();
		this.productoServicio = productoServicio;
	}
	
	public Pedido nuevoPedido(CreatePedidoDTO nuevoPedido, Usuario cliente) {
		Pedido pedidoAguardar = new Pedido();
		
		pedidoAguardar.setCliente(cliente);
		pedidoAguardar.setLineas(nuevoPedido
				.getLineas().stream()
				.map( lineaDto -> {
					Optional<Producto> p = productoServicio.findById(lineaDto.getProductoId());
					
					if(p.isPresent()) {
						Producto producto = p.get();
						LineaPedido lineaPedido = new LineaPedido();
						lineaPedido.setCantidad(lineaDto.getCantidad());
						lineaPedido.setPrecio(producto.getPrecio());
						lineaPedido.setProducto(producto);
						return lineaPedido;
					} else {
						return null;
					}
			
		})
		.filter(Objects::nonNull)
		.collect(Collectors.toSet()));
		
		return save(pedidoAguardar);
	}
	
	public Page<Pedido> findAllByUser(Usuario usuario, Pageable pageable){
		return repositorio.findByCliente(usuario, pageable);
	}
}
