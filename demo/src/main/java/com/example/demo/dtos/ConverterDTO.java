package com.example.demo.dtos;

import com.example.demo.modelos.Producto;

public class ConverterDTO {

	public static ProductoDTO productoDTOconverter(Producto producto) {
		ProductoDTO dto = new ProductoDTO();
		dto.setId(producto.getId());
		dto.setNombre(producto.getNombre());
		dto.setCategoriaNombre(producto.getCategoria().getNombre());

		return dto;
	}
}
