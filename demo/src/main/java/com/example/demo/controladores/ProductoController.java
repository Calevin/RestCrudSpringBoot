package com.example.demo.controladores;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.dtos.producto.CreateProductoDTO;
import com.example.demo.dtos.producto.GetProductoDTO;
import com.example.demo.dtos.producto.ProductoConverterDTO;
import com.example.demo.dtos.views.ProductoViews;
import com.example.demo.errores.NotFoundException;
import com.example.demo.modelos.Producto;
import com.example.demo.servicios.ProductoServicio;
import com.example.demo.util.paginacion.PaginacionLinksUtils;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class ProductoController {

	private final ProductoConverterDTO converterDTO;
	private final ProductoServicio productoServicio;
	private final PaginacionLinksUtils paginacionLinksUtils;
	

	@Autowired
	public ProductoController(ProductoServicio productoServicio
			, ProductoConverterDTO converterDTO
			, PaginacionLinksUtils paginacionLinksUtils) {
		super();
		this.productoServicio = productoServicio;
		this.converterDTO = converterDTO;
		this.paginacionLinksUtils = paginacionLinksUtils;
	}

	/**
	 * Obtenemos todos los productos
	 * 
	 * @return
	 */
	@GetMapping("/producto")
	public ResponseEntity<List<GetProductoDTO>> obtenerTodos() {
		List<Producto> productos = productoServicio.findAll();
		if (productos.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(productos.stream()
										.map(converterDTO::convertProductoToGetProductoDTO)
										.collect(Collectors.toList()));
		}
	}
	
	@GetMapping("/producto_paginado")
	public ResponseEntity<?> obtenerTodosPaginado(
			@PageableDefault(size=10, page=0) Pageable pageable
			, HttpServletRequest request) {
		
		Page<Producto> productos = productoServicio.findAll(pageable);
		
		if (productos.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			Page<GetProductoDTO> dtoList = productos.map(converterDTO::convertProductoToGetProductoDTO);
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			
			return ResponseEntity
					.ok()
					.header("link", paginacionLinksUtils.createLinkHeader(dtoList, uriBuilder))
					.body(dtoList);
		}
	}
	
	/**
	 * Obtener un listado de productos por nombre
	 * @param txt Cadena de caracteres que se usará para buscar en el nombre
	 * @return 404 si no se encuentran resultados. 200 y el conjunto de productos si se encuentra
	 */
	@GetMapping(value = "/producto", params = "nombre")
	public ResponseEntity<?> buscarProductosPorNombre(
			@RequestParam("nombre") String txt,
			Pageable pageable, HttpServletRequest request) {
		
		Page<Producto> result = productoServicio.findByNombre(txt, pageable);		
	
		if (result.isEmpty()) {
			throw new NotFoundException();
		} else {

			Page<GetProductoDTO> dtoList = result.map(converterDTO::convertProductoToGetProductoDTO);
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

			return ResponseEntity
					.ok()
					.header("link", paginacionLinksUtils.createLinkHeader(dtoList, uriBuilder))
					.body(dtoList);
		}
	}
	
	@GetMapping(value = "/producto_by_args")
	public ResponseEntity<?> buscarProductosPorVarios(
			@RequestParam("nombre") Optional<String> txt,
			@RequestParam("precio") Optional<Float> precio,
			Pageable pageable, HttpServletRequest request) {
		
		Page<Producto> result = productoServicio.findByArgs(txt, precio, pageable);
	
		if (result.isEmpty()) {
			throw new NotFoundException();
		} else {

			Page<GetProductoDTO> dtoList = result.map(converterDTO::convertProductoToGetProductoDTO);
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

			return ResponseEntity.ok().header("link", paginacionLinksUtils.createLinkHeader(dtoList, uriBuilder))
					.body(dtoList);
		}
		
	}
	
	@JsonView(ProductoViews.DtoConPrecio.class)
	@GetMapping(value = "/producto_by_args_dto")
	public ResponseEntity<?> buscarProductosPorVariosDto(
			@RequestParam("nombre") Optional<String> txt,
			@RequestParam("precio") Optional<Float> precio,
			Pageable pageable, HttpServletRequest request) {
		
		Page<Producto> result = productoServicio.findByArgs(txt, precio, pageable);
	
		if (result.isEmpty()) {
			throw new NotFoundException();
		} else {

			Page<GetProductoDTO> dtoList = result.map(converterDTO::convertProductoToGetProductoDTO);
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

			return ResponseEntity.ok().header("link", paginacionLinksUtils.createLinkHeader(dtoList, uriBuilder))
					.body(dtoList);
		}
		
	}
	
	/**
	 * Obtenemos un producto en base a su ID
	 * 
	 * @param id
	 * @return Null si no encuentra el producto
	 */
	@GetMapping("/producto/{id}")
	public GetProductoDTO obtenerUno(@PathVariable Long id) {
		return productoServicio.findById(id).map(converterDTO::convertProductoToGetProductoDTO)
				.orElseThrow(() -> new NotFoundException(id));
	}

	/**
	 * Insertamos un nuevo producto
	 * 
	 * @param nuevo
	 * @return producto insertado
	 */
	@PostMapping("/producto")
	public ResponseEntity<Producto> nuevoProducto(@RequestBody CreateProductoDTO createProducto) {
		Producto productoNuevo =  converterDTO.convertCreateProductoToProducto(createProducto);
		return ResponseEntity.status(HttpStatus.CREATED).body(productoServicio.save(productoNuevo));
	}

	/**
	 * 
	 * @param editar
	 * @param id
	 * @return
	 */
	@PutMapping("/producto/{id}")
	public Producto editarProducto(@RequestBody Producto editar, @PathVariable Long id) {
		return productoServicio
				.findById(id)
				.map(p -> productoServicio.save(editar))
				.orElseThrow(() -> new NotFoundException(id));
	}

	/**
	 * Borra un producto del catálogo en base a su id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?> borrarProducto(@PathVariable Long id) {
		return productoServicio
				.findById(id)
				.map( p -> {
					productoServicio.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElseThrow(() -> new NotFoundException(id));
	}
}
