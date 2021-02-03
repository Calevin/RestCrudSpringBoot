package com.example.demo.dtos.pedido;

import java.util.List;

public class CreatePedidoDTO {
	
	private List<CreateLineaPedidoDto> lineas;
	
	public CreatePedidoDTO() {
		super();
	}
	
	public CreatePedidoDTO(List<CreateLineaPedidoDto> lineas) {
		super();
		this.lineas = lineas;
	}
	
	public List<CreateLineaPedidoDto> getLineas() {
		return lineas;
	}

	public void setLineas(List<CreateLineaPedidoDto> lineas) {
		this.lineas = lineas;
	}
	
	public static class CreateLineaPedidoDto {
		
		private int cantidad;
		private Long productoId;
		
		public CreateLineaPedidoDto() {
			super();
		}
		
		public CreateLineaPedidoDto(int cantidad, Long productoId) {
			super();
			this.cantidad = cantidad;
			this.productoId = productoId;
		}
		
		public int getCantidad() {
			return cantidad;
		}
		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
		public Long getProductoId() {
			return productoId;
		}
		public void setProductoId(Long productoId) {
			this.productoId = productoId;
		}		
	}
}
