package com.example.demo.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.errores.NotFoundException;
import com.example.demo.modelos.Producto;
import com.example.demo.servicios.ProductoRepositorio;

@RestController
public class ProductoController {

	private final ProductoRepositorio productoRepositorio;

	@Autowired
	public ProductoController(ProductoRepositorio productoRepositorio) {
		super();
		this.productoRepositorio = productoRepositorio;
	}

	/**
	 * Obtenemos todos los productos
	 * 
	 * @return
	 */
	@GetMapping("/producto")
	public ResponseEntity<List<Producto>> obtenerTodos() {
		List<Producto> productos = productoRepositorio.findAll();
		if (productos.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(productos);
		}
	}

	/**
	 * Obtenemos un producto en base a su ID
	 * 
	 * @param id
	 * @return Null si no encuentra el producto
	 */
	@GetMapping("/producto/{id}")
	public ResponseEntity<Producto> obtenerUno(@PathVariable Long id) {
		return productoRepositorio.findById(id)
				.map(p -> ResponseEntity.ok(p))
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Insertamos un nuevo producto
	 * 
	 * @param nuevo
	 * @return producto insertado
	 */
	@PostMapping("/producto")
	public ResponseEntity<Producto> nuevoProducto(@RequestBody Producto nuevo) {
		return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.save(nuevo));
	}

	/**
	 * 
	 * @param editar
	 * @param id
	 * @return
	 */
	@PutMapping("/producto/{id}")
	public ResponseEntity<Producto> editarProducto(@RequestBody Producto editar, @PathVariable Long id) {
		return productoRepositorio
				.findById(id)
				.map( p -> ResponseEntity.ok(productoRepositorio.save(editar)))
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Borra un producto del catálogo en base a su id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?> borrarProducto(@PathVariable Long id) {
		return productoRepositorio
				.findById(id)
				.map( p -> {
					productoRepositorio.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}
