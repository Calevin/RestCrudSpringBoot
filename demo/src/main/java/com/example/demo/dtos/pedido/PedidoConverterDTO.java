package com.example.demo.dtos.pedido;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.modelos.LineaPedido;
import com.example.demo.modelos.Pedido;

@Component
public class PedidoConverterDTO {

	public GetPedidoDTO converterPedidoToGetPedido(Pedido pedido) {
		GetPedidoDTO getPedidoDTO = new GetPedidoDTO();
		
		getPedidoDTO.setAvatar(pedido.getCliente().getAvatar());
		getPedidoDTO.setFecha(pedido.getFecha()); 
		getPedidoDTO.setTotal(pedido.getTotal());
		getPedidoDTO.setLineas(pedido
				.getLineas().stream()
				.map(this::convertLineaPedidoToGetLineaPedidoDto)
				.collect(Collectors.toSet()));
		
		return getPedidoDTO;
	}
	
	public GetPedidoDTO.GetLineaPedidoDTO convertLineaPedidoToGetLineaPedidoDto(LineaPedido linea) {
		GetPedidoDTO.GetLineaPedidoDTO getLineaPedidoDTO = new GetPedidoDTO.GetLineaPedidoDTO();
		
		getLineaPedidoDTO.setCantidad(linea.getCantidad());
		getLineaPedidoDTO.setPrecioUnitario(linea.getPrecio());
		getLineaPedidoDTO.setProductoNombre(linea.getProducto().getNombre()); 
		getLineaPedidoDTO.setSubTotal(linea.getSubtotal());
		
		return getLineaPedidoDTO;
	}
}
