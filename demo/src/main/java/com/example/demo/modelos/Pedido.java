package com.example.demo.modelos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Pedido {
	@Id
	@GeneratedValue
	private Long id;

	private String cliente;

	@CreatedDate
	private LocalDateTime fecha;

	@JsonManagedReference
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LineaPedido> lineas = new HashSet<>();

	public Pedido() {
		super();
	}

	public Pedido(Long id, String cliente, LocalDateTime fecha, Set<LineaPedido> lineas) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.fecha = fecha;
		this.lineas = lineas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Set<LineaPedido> getLineas() {
		return lineas;
	}

	public void setLineas(Set<LineaPedido> lineas) {
		this.lineas = lineas;
	}

	public float getTotal() {
		return (float) lineas.stream().mapToDouble(LineaPedido::getSubtotal).sum();
	}

	public void addLineaPedido(LineaPedido lp) {
		lineas.add(lp);
		lp.setPedido(this);
	}

	public void removeLineaPedido(LineaPedido lp) {
		lineas.remove(lp);
		lp.setPedido(null);
	}
}
