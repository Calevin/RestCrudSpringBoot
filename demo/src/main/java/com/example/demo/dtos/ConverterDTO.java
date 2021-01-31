package com.example.demo.dtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.modelos.Categoria;
import com.example.demo.modelos.Producto;
import com.example.demo.servicios.CategoriaRepositorio;

@Component
public class ConverterDTO {
	
	@Autowired
	CategoriaRepositorio categoriaRepo;
	
	public ConverterDTO() {
	}

	public ProductoDTO productoDTOconverter(Producto producto) {
		ProductoDTO dto = new ProductoDTO();
		dto.setId(producto.getId());
		dto.setNombre(producto.getNombre());
		dto.setCategoriaNombre(producto.getCategoria().getNombre());

		return dto;
	}
	
	public Producto createProductoDTOconverter(CreateProductoDTO createProducto) {
		Producto producto = new Producto();
		producto.setNombre(createProducto.getNombre());
		producto.setPrecio(createProducto.getPrecio()); 
		
		Categoria categoria = categoriaRepo.findById(createProducto.getCategoriaId()).get();
		
		producto.setCategoria(categoria);

		return producto;
	}
}