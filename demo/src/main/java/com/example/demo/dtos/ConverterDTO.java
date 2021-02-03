package com.example.demo.dtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dtos.producto.CreateProductoDTO;
import com.example.demo.dtos.producto.GetProductoDTO;
import com.example.demo.modelos.Categoria;
import com.example.demo.modelos.Producto;
import com.example.demo.repositorios.CategoriaRepositorio;

@Component
public class ConverterDTO {
	
	@Autowired
	CategoriaRepositorio categoriaRepo;
	
	public ConverterDTO() {
	}

	public GetProductoDTO productoDTOconverter(Producto producto) {
		GetProductoDTO dto = new GetProductoDTO();
		dto.setId(producto.getId());
		dto.setNombre(producto.getNombre());
		dto.setCategoriaNombre(producto.getCategoria().getNombre());
		dto.setPrecio(producto.getPrecio());

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
