package com.example.demo.dtos.producto;

import com.example.demo.dtos.views.ProductoViews;
import com.fasterxml.jackson.annotation.JsonView;

public class GetProductoDTO {

	@JsonView(ProductoViews.Dto.class)
	private long id;
	
	@JsonView(ProductoViews.Dto.class)
	private String nombre;
	
	@JsonView(ProductoViews.Dto.class)
	private String categoriaNombre;
	
	@JsonView(ProductoViews.DtoConPrecio.class)
	private Float precio;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoriaNombre() {
		return categoriaNombre;
	}

	public void setCategoriaNombre(String categoriaNombre) {
		this.categoriaNombre = categoriaNombre;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}
}
