package com.example.demo.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "linea_pedido")
public class LineaPedido {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "producto_id")
	private Producto producto;

	private float precio;

	private int cantidad;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	public LineaPedido() {
		super();
	}

	public LineaPedido(Long id, Producto producto, float precio, int cantidad, Pedido pedido) {
		super();
		this.id = id;
		this.producto = producto;
		this.precio = precio;
		this.cantidad = cantidad;
		this.pedido = pedido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public float getSubtotal() {
		return precio * cantidad;
	}
}
