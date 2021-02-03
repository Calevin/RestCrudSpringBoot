package com.example.demo.dtos.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.modelos.Categoria;
import com.example.demo.modelos.Producto;
import com.example.demo.repositorios.CategoriaRepositorio;

@Component
public class ProductoConverterDTO {
	
	@Autowired
	CategoriaRepositorio categoriaRepo;
	
	public ProductoConverterDTO() {
	}

	public GetProductoDTO convertProductoToGetProductoDTO(Producto producto) {
		GetProductoDTO dto = new GetProductoDTO();
		dto.setId(producto.getId());
		dto.setNombre(producto.getNombre());
		dto.setCategoriaNombre(producto.getCategoria().getNombre());
		dto.setPrecio(producto.getPrecio());

		return dto;
	}
	
	public Producto convertCreateProductoToProducto(CreateProductoDTO createProducto) {
		Producto producto = new Producto();
		producto.setNombre(createProducto.getNombre());
		producto.setPrecio(createProducto.getPrecio()); 
		
		Categoria categoria = categoriaRepo.findById(createProducto.getCategoriaId()).get();
		
		producto.setCategoria(categoria);

		return producto;
	}
}
