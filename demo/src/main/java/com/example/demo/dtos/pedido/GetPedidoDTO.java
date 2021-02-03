package com.example.demo.dtos.pedido;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class GetPedidoDTO {
	private String avatar;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime fecha;
	private Set<GetLineaPedidoDTO> lineas;
	private float total;

	public GetPedidoDTO() {
		super();
	}

	public GetPedidoDTO(String fullName, String email, String avatar, LocalDateTime fecha,
			Set<GetLineaPedidoDTO> lineas, float total) {
		super();
		this.avatar = avatar;
		this.fecha = fecha;
		this.lineas = lineas;
		this.total = total;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Set<GetLineaPedidoDTO> getLineas() {
		return lineas;
	}

	public void setLineas(Set<GetLineaPedidoDTO> lineas) {
		this.lineas = lineas;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public static class GetLineaPedidoDTO {

		private String productoNombre;
		private int cantidad;
		private float precioUnitario;
		private float subTotal;

		public GetLineaPedidoDTO() {
			super();
		}

		public GetLineaPedidoDTO(String productoNombre, int cantidad, float precioUnitario, float subTotal) {
			super();
			this.productoNombre = productoNombre;
			this.cantidad = cantidad;
			this.precioUnitario = precioUnitario;
			this.subTotal = subTotal;
		}

		public String getProductoNombre() {
			return productoNombre;
		}

		public void setProductoNombre(String productoNombre) {
			this.productoNombre = productoNombre;
		}

		public int getCantidad() {
			return cantidad;
		}

		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}

		public float getPrecioUnitario() {
			return precioUnitario;
		}

		public void setPrecioUnitario(float precioUnitario) {
			this.precioUnitario = precioUnitario;
		}

		public float getSubTotal() {
			return subTotal;
		}

		public void setSubTotal(float subTotal) {
			this.subTotal = subTotal;
		}
	}
}
